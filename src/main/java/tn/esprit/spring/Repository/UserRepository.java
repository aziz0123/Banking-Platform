package tn.esprit.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.Entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserById(Long id);
    User findByEmail(String email);


    Optional<User> findOptionalUserByEmail(String email);

    boolean existsByEmail(String email);
    User findByVerificationToken(String verificationToken);
    List<User> findByLastLoginBefore(LocalDate date);
}
