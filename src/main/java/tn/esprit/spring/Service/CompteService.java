package tn.esprit.spring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Repository.CompteRepository;
import tn.esprit.spring.Repository.UserRepository;
import tn.esprit.spring.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CompteService implements ICompteService{
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Compte  getCompteById(Long id) {
        return compteRepository.findCompteById(id);
    }



    public Compte createCompte(Compte compte, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            compte.setProprietaire(user);
            Compte c= compteRepository.save(compte);
            user.getComptes().add(compte);
            userRepository.save(user);
            return c;
        } else {
            throw new RuntimeException("Utilisateur non trouvé avec l'email : " + email);
        }
    }

    public Compte updateCompte(Long id, Compte compteDetails) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compte not found"));
        compte.setNumeroCompte(compteDetails.getNumeroCompte());
        compte.setSolde(compteDetails.getSolde());
        compte.setProprietaire(compteDetails.getProprietaire());
        compte.setTypeCompte(compteDetails.getTypeCompte());
        compte.setDateOuverture(compteDetails.getDateOuverture());
        compte.setTransactions(compteDetails.getTransactions());
        return compteRepository.save(compte);
    }

    public void deleteCompte(Long id) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compte not found"));
        compteRepository.delete(compte);
    }

    public double getSolde(Long id) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compte not found"));
        return compte.getSolde();
    }


    public Compte updateSolde(Long id, double newSolde) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Compte not found"));
        compte.setSolde(newSolde);
        return compteRepository.save(compte);
    }
    public List<Compte> getComptesByUserEmail(String email) {
        Optional<User> user = userRepository.findOptionalUserByEmail(email);
        if (user.isPresent()) {
            return compteRepository.findByUserId(user.get().getId());
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

}
