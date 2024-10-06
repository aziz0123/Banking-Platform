package tn.esprit.spring.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.Security.JwtIssuer;
import tn.esprit.spring.Security.UserPrincipal;
import tn.esprit.spring.errors.UserNotFoundException;
import tn.esprit.spring.model.LoginResponce;
import tn.esprit.spring.model.RegisterDto;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService  {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    public LoginResponce attemptLogin(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponce.builder()
                .accessToken(token)
                .build();
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return authorities;
    }

    public void registerUser(RegisterDto registerDto) {
        if (userRepo.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Username is taken!");
        }
        User user = new User();
        user.setNom(registerDto.getNom());
        user.setPrenom(registerDto.getPrenom());
        user.setCin(registerDto.getCin());
        user.setDateN(registerDto.getDateN());
        user.setUpdatedAt(new Date());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(new Date());

        String token = generateVerificationToken();
        user.setVerificationToken(token);

        System.out.println(user);
        userRepo.save(user);
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public void resetPassword(String email, String password) {
        User u = userRepo.findByEmail(email);
        System.out.println(email);
        if (u != null) {
            u.setPassword(passwordEncoder.encode(password));
            System.out.println("done");
            u.setUpdatedAt(new Date());
            userRepo.save(u);
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    public boolean isOldPasswordCorrect(String email, String oldPass) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(oldPass, user.getPassword());
        } else {
            return false;
        }
    }

    public void lastLogin(String email) {
        User u = userRepo.findByEmail(email);
        u.setLastLogin(LocalDate.now());
        userRepo.save(u);
    }


}
