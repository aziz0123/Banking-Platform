package tn.esprit.spring.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude  // Exclure ce champ du toString pour éviter la récursion
    @EqualsAndHashCode.Exclude
    private Long Id;
    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private String password;
    private LocalDate dateN;
    private String telephone;

    private Role role = Role.USER;

    @JsonManagedReference  // Utilisé pour gérer la sérialisation JSON
    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL)
    @ToString.Exclude  // Éviter la récursion
    private Set<Compte> comptes = new HashSet<>();

    @OneToMany
    @ToString.Exclude  // Éviter la récursion
    private Set<DemandePret> prets = new HashSet<>();

    private Date updatedAt;
    private Date createdAt;
    private boolean enabled = false;
    private LocalDate lastLogin;
    private String verificationToken;

    public boolean getEnabled() {
        return this.enabled;
    }
}
