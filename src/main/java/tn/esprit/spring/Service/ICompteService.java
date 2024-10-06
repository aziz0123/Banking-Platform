package tn.esprit.spring.Service;

import org.springdoc.api.OpenApiResourceNotFoundException;
import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ICompteService {
    public List<Compte> getAllComptes() ;



    public Compte createCompte(Compte compte, String email);

    public Compte updateCompte(Long id, Compte compteDetails) ;

    public void deleteCompte(Long id) ;
    public double getSolde(Long id) ;

    // Nouveau : modifier le solde
    public Compte updateSolde(Long id, double newSolde) ;
}
