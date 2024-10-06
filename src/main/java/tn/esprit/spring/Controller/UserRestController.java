package tn.esprit.spring.Controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.Service.AuthService;
import tn.esprit.spring.Service.IUserService;
import tn.esprit.spring.errors.PasswordDoesNotMatchTheOld;
import tn.esprit.spring.errors.UserNotFoundException;
import tn.esprit.spring.model.ChangePasswordRequest;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {
    @Autowired
    IUserService iUserService ;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepo;

    @GetMapping("findUserByUsername/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = iUserService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/updateUser/{email}")
    public ResponseEntity<User> editUser(@PathVariable("email") String email, @RequestBody User user) {
        // Make sure to set the username in the User object if it's not already set
        user.setEmail(email);
        User updatedUser = iUserService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        if (request.getEmail() == null ||
                request.getOldPass().isEmpty() ||
                request.getNewPass() == null) {
            System.out.println("Email, old password, and new password are required");
        }

        if (!authService.isOldPasswordCorrect(request.getEmail(), request.getOldPass())) {
            throw new PasswordDoesNotMatchTheOld("The entered old password does not match the current password");
        }
        String subject = "Password Change Notification";
        String body = "Your password has been changed.\n if it wasnt u, \n Click http://localhost:4200/reset-password/" + request.getEmail() + " to change your password.";
        iUserService.changePassword(request.getEmail(), request.getOldPass(), request.getNewPass());
    }

    @PutMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email, @RequestParam String password) {
        try {
            authService.resetPassword(email, password);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }



    @PutMapping("toggelUser")
    User enableOrDisable( @RequestParam String email){
        return iUserService.enableOrDisable(email);
    }


    @GetMapping("SendEmail")
    public void sendEmail(@RequestParam("email") String email) {
        User user = iUserService.findByEmail(email);
        if (user.getId() == null) {
            throw new UserNotFoundException("User not found by the provided email");
        } else {
            String subject = "Rest your password";
            String body = "Click on the lick below to rest your password \n http://localhost:4200/reset-password/" + email;
        }

    }


    @GetMapping("/allusers")
    @ResponseBody
    public List<User> retrievealluser (){
        return iUserService.retrieveAllUser();
    }


    @GetMapping("/getRoleByEmail/{email}")
    @ResponseBody
    Role getRoleUserByEmail(@PathVariable("email") String email){
        return iUserService.getRoleByEmail(email);
    }


    @DeleteMapping("/deleteuser/{email}")
    public void deleteUser (@PathVariable ("email") String email){
        iUserService.deleteUser(email);
    }

}


