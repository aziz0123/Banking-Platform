package tn.esprit.spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Entity.PasswordResetToken;
import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.PasswordResetTokenRepository;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.errors.UserNotFoundException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;



    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found by email");
        }
        return user;
    }
    public User findById(Long id){
        User user = userRepo.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found by email");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        User u = userRepo.findByEmail(user.getEmail());
        if (u != null) {
            u.setNom(user.getNom());
            u.setPrenom(user.getPrenom());
            return userRepo.save(u);
        } else {
            return null;
        }
    }

    @Override
    public void saveVerificationToken(Long id, String verfi) {
        User u = userRepo.findUserById( id);
        u.setVerificationToken(verfi);
        userRepo.save(u);
    }

    @Override
    public User findByVerificationToken(String verificationToken) {
        return userRepo.findByVerificationToken(verificationToken);
    }

    @Override
    public User enableOrDisable(String email) {
        User u = userRepo.findByEmail(email);
        if (u != null) {
            boolean isEnabled = u.getEnabled();
            u.setEnabled(!isEnabled);
            userRepo.save(u);
            return u;
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        if(userRepo.existsByEmail(email)) {
            User user = userRepo.findByEmail(email);
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepo.save(user);
            } else {
                throw new BadCredentialsException("Incorrect old password");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    @Override
    public void disableInactiveAccounts() {
        List<User> inactiveUsers = userRepo.findByLastLoginBefore(LocalDate.now().minusDays(90));
        for (User user : inactiveUsers) {
            user.setEnabled(false);
            userRepo.save(user);
        }
    }



    @Override
    public List<User> retrieveAllUser() {
        return userRepo.findAll();
    }


    @Override
    public void deleteUser(String email){
        User u = userRepo.findByEmail(email);
        userRepo.delete(u);
    }


    @Override
    public Role getRoleByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return user.getRole();
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
    //Forgot  password
    public String generatePasswordResetToken(String email) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
            tokenRepository.save(passwordResetToken);
            return token;
        }
        return null;
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.isPresent() && !resetToken.get().isExpired();
    }

    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        if (resetToken.isPresent()) {
            User user = resetToken.get().getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        }
    }

}
