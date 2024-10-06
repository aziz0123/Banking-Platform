package tn.esprit.spring.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.DemandePret;
import tn.esprit.spring.Entity.StatusDemande;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.DemandePretRepository;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.Service.DemandePretService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("DemandePret")
public class DemandePretController {
    private final DemandePretRepository demandePretRepository;
    private final UserRepository userRepository;
    @Autowired
    private DemandePretService demandePretService;

    @PostMapping("/createDemandePret")
    public DemandePret createDemandePret(String email, double montant) {
        // Récupérer l'utilisateur depuis la base de données
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        DemandePret demandePret = new DemandePret();
        demandePret.setUser(user);
        demandePret.setMontant(montant);
        // Configurez d'autres propriétés si nécessaire

        return demandePretRepository.save(demandePret);
    }
//Get

    @GetMapping("/user/{userId}/demandes-pret")
    public List<DemandePret> getDemandesPretByUser(@PathVariable Long userId) {
        return demandePretService.findDemandePretByUser(userId);
    }
    @GetMapping("/user/{email}/demandes-pret")
    public List<DemandePret> getDemandePretByUserEmail(@PathVariable String email){
        return demandePretService.findDemandePretByUserEmail(email);
    }

    @PutMapping("/statusDemandePret/{id}")
    public ResponseEntity<DemandePret> changeStatus(@PathVariable Long id, @RequestParam StatusDemande status) {
        DemandePret updatedDemandePret = demandePretService.changeStatus(id, status);
        return updatedDemandePret != null ?
                ResponseEntity.ok(updatedDemandePret) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update-montantPret/{id}")
    public ResponseEntity<DemandePret> updateMontant(@PathVariable Long id, @RequestParam double montant) {
        DemandePret updatedDemandePret = demandePretService.updateMontant(id, montant);
        return updatedDemandePret != null ?
                ResponseEntity.ok(updatedDemandePret) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/allDemandePret")
    public ResponseEntity<List<DemandePret>> getAllDemandesPret() {
        List<DemandePret> demandesPret = demandePretService.getAllDemandesPret();
        return ResponseEntity.ok(demandesPret);
    }

    @DeleteMapping("/deleteDemandePret/{id}")
    public ResponseEntity<Void> deleteDemandePret(@PathVariable Long id) {
        demandePretService.deleteDemandePret(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
