package tn.esprit.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.Entity.Compte;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte,Long> {
    @Override
    Optional<Compte> findById(Long aLong);
    Compte findCompteById(Long id);
    @Query("SELECT c FROM Compte c WHERE c.proprietaire.Id = :userId")
    List<Compte> findByUserId(@Param("userId") Long userId);

    Optional<Compte> findByNumeroCompte(Long num);
}
