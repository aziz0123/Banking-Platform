package tn.esprit.spring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DemandePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long idDemandePret;
    @ManyToOne
    private User user;
    private double montant;
    private double tauxInteret;
    private StatusDemande statusDemande;
    private Date dateDemande;
    private Date dateMAJ;

}
