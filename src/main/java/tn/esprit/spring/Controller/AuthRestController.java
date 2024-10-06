package tn.esprit.spring.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.Otp;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.Security.JwtIssuer;
import tn.esprit.spring.Service.AuthService;
import tn.esprit.spring.Service.EmailService;
import tn.esprit.spring.Service.OtpService;
import tn.esprit.spring.Service.UserService;
import tn.esprit.spring.errors.InvalidCredentials;
import tn.esprit.spring.errors.UserNotEnabled;
import tn.esprit.spring.errors.UserNotFoundException;
import tn.esprit.spring.model.LoginRequest;
import tn.esprit.spring.model.LoginResponce;
import tn.esprit.spring.model.RegisterDto;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRestController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final EmailService emailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private UserService userService;


    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponce> login(@RequestBody @Validated LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            System.out.println("Email and password are required");
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Attempting login for email: " + request.getEmail());

        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found by email");
        }
        if (!user.getEnabled()) {
            throw new UserNotEnabled("User is not authorized");
        }
        try {
            LoginResponce loginResponse = authService.attemptLogin(request.getEmail(),request.getPassword());
            authService.lastLogin(request.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // Catch the specific exception and rethrow it as InvalidCredentials
            throw new InvalidCredentials("Invalid credentials: " + e.getMessage());
        }
    }
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        User user = userRepo.findByEmail(registerDto.getEmail());
        if (user != null) {
            throw new UserNotFoundException("User exist by that email");
        }
        authService.registerUser(registerDto);
        emailService.sendCodeByMail(registerDto.getEmail());
        return ResponseEntity.ok("User registered successfully");
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("verificationToken") String verificationToken) {
        if (validateVerificationToken(verificationToken)) {
            return ResponseEntity.ok("User verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }
    }

    private boolean validateVerificationToken(String verificationToken) {
        User u = userRepo.findByVerificationToken(verificationToken);
        if (u.getVerificationToken().equals(verificationToken)) {
            if (u.getEnabled()) {
                return true;
            }else if(!u.getEnabled()){
                u.setEnabled(true);
                userRepo.save(u);
                return true;
            }
        }
        return false;
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestParam String email) {
        User existingUser = userService.findByEmail(email);
        if (existingUser != null) {
            String otp = otpService.generateOTP();
            otpService.sendOTPEmail(existingUser.getEmail(), otp);
            otpService.saveOTP(existingUser.getEmail(), otp);
            return ResponseEntity.ok("OTP sent to your email");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Otp otpRequest) {
        boolean isValid = otpService.verifyOTP(otpRequest.getEmail(), otpRequest.getOtp());
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
        }
    }
    //Forgot Password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String token = userService.generatePasswordResetToken(email);
        if (token != null) {
            String resetUrl = " reset Token =" + token;
            otpService.sendOTPEmail2(email, resetUrl);
            return ResponseEntity.ok("Password token sent to your email");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (userService.validatePasswordResetToken(token)) {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
}
