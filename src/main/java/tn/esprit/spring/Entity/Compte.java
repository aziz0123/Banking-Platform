package tn.esprit.spring.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude  // Exclure ce champ du toString pour éviter la récursion
    @EqualsAndHashCode.Exclude
    private Long id;

    private Long numeroCompte;
    private double solde;

    @JsonBackReference  // Utilisé pour gérer la sérialisation JSON (empêche la récursion)
    @ManyToOne
    @ToString.Exclude  // Éviter la récursion
    private User proprietaire;

    private TypeCompte typeCompte;
    private Date dateOuverture;

    @OneToMany(mappedBy = "compteSource", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Éviter la récursion
    private Set<Transaction> transactions = new HashSet<>();

}
