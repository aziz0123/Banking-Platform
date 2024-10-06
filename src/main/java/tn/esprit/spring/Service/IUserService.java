package tn.esprit.spring.Service;



import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Entity.User;

import java.util.List;

public interface IUserService {
    User findByEmail(String email);
    User updateUser (User user);
    void changePassword(String email, String newPassword, String oldPssword);
    User enableOrDisable(String id);
    void saveVerificationToken(Long id,String verfi);
    User findByVerificationToken(String verificationToken);
    void disableInactiveAccounts();

    List<User> retrieveAllUser();


    void deleteUser(String Id);

    Role getRoleByEmail(String email);
}
