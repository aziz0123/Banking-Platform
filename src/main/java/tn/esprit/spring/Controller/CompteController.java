package tn.esprit.spring.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.Service.CompteService;
import tn.esprit.spring.Service.ICompteService;
import tn.esprit.spring.errors.ResourceNotFoundException;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("Compte")
public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping("/getAllComptes")
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> comptes = compteService.getAllComptes();
        System.out.println("Comptes: " + comptes); // Ajoutez cette ligne pour vérifier les données
        return ResponseEntity.ok(comptes);
    }

    @GetMapping("/getCompteById/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Compte compte = compteService.getCompteById(id);
        return ResponseEntity.ok(compte);
    }

    @PostMapping("/createCompte")
    public ResponseEntity<Compte> createCompte(@RequestBody Compte compte, @RequestParam String email) {
        try {
            Compte createdCompte = compteService.createCompte(compte, email);
            return new ResponseEntity<>(createdCompte, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/userAcounts")
    public ResponseEntity<List<Compte>> getComptesByUserEmail(@RequestParam String email) {
        List<Compte> comptes = compteService.getComptesByUserEmail(email);
        return ResponseEntity.ok(comptes);
    }

    @PutMapping("/updateCompte/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compteDetails) {
        Compte updatedCompte = compteService.updateCompte(id, compteDetails);
        return ResponseEntity.ok(updatedCompte);
    }

    @DeleteMapping("deleteCompte/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("getSolde/{id}/solde")
    public ResponseEntity<Double> getSolde(@PathVariable Long id) {
        double solde = compteService.getSolde(id);
        return ResponseEntity.ok(solde);
    }

    @PutMapping("updateSolde/{id}/solde")
    public ResponseEntity<Compte> updateSolde(@PathVariable Long id, @RequestParam double newSolde) {
        Compte updatedCompte = compteService.updateSolde(id, newSolde);
        return ResponseEntity.ok(updatedCompte);
    }
}
