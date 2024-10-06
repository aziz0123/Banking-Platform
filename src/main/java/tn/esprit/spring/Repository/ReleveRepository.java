package tn.esprit.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.Entity.Releve;
@Repository
public interface ReleveRepository extends JpaRepository<Releve,Long> {
}
