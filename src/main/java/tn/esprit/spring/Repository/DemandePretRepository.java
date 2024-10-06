package tn.esprit.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.Entity.DemandePret;

import java.util.List;

@Repository
public interface DemandePretRepository extends JpaRepository<DemandePret,Long> {
    @Query("SELECT dp FROM DemandePret dp WHERE dp.user.Id = :userId")
    List<DemandePret> findByUserId(@Param("userId") Long userId);
    @Query("SELECT dp FROM DemandePret dp WHERE dp.user.email = :email")
    List<DemandePret> findByUserEmail(@Param("email") String email);

}
