package tn.esprit.spring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Entity.DemandePret;
import tn.esprit.spring.Entity.StatusDemande;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.DemandePretRepository;
import tn.esprit.spring.Repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DemandePretService implements IDemandePretService{
    @Autowired
    private DemandePretRepository demandePretRepository;
    @Autowired
    private UserRepository userRepository;

    public DemandePret createDemandePret(String email, double montant) {
        // Rechercher l'utilisateur par email
        User user = userRepository.findOptionalUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email : " + email));

        // Créer une nouvelle demande de prêt
        DemandePret demandePret = new DemandePret();
        demandePret.setUser(user);
        demandePret.setMontant(montant);
        demandePret.setTauxInteret(0.12);
        demandePret.setStatusDemande(StatusDemande.EN_ATTENTE); // Assurez-vous de définir une valeur par défaut
        demandePret.setDateDemande(new Date());
        demandePret.setDateMAJ(new Date());
        user.getPrets().add(demandePret);
        userRepository.save(user);

        // Sauvegarder la demande de prêt
        return demandePretRepository.save(demandePret);
    }

    // Méthode pour changer le statut d'une DemandePret
    public DemandePret changeStatus(Long id, StatusDemande newStatus) {
        Optional<DemandePret> demandePretOptional = demandePretRepository.findById(id);
        if (demandePretOptional.isPresent()) {
            DemandePret demandePret = demandePretOptional.get();
            demandePret.setStatusDemande(newStatus);
            return demandePretRepository.save(demandePret);
        }
        return null;
    }

    // Méthode pour mettre à jour le montant d'une DemandePret
    public DemandePret updateMontant(Long id, double newMontant) {
        Optional<DemandePret> demandePretOptional = demandePretRepository.findById(id);
        if (demandePretOptional.isPresent()) {
            DemandePret demandePret = demandePretOptional.get();
            demandePret.setMontant(newMontant);
            return demandePretRepository.save(demandePret);
        }
        return null;
    }
    //GET
    public List<DemandePret> findDemandePretByUser(Long userId) {
        return demandePretRepository.findByUserId(userId);
    }
    public List<DemandePret> findDemandePretByUserEmail(String email) {
        return demandePretRepository.findByUserEmail(email);
    }

    // Méthode pour récupérer toutes les DemandePret
    public List<DemandePret> getAllDemandesPret() {
        return demandePretRepository.findAll();
    }

    // Méthode pour supprimer une DemandePret
    public void deleteDemandePret(Long id) {
        demandePretRepository.deleteById(id);
    }
}
