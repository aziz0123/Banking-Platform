package tn.esprit.spring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long idTransaction;
    private double montant;
    private Date dateDeTransaction;
    @ManyToOne
    @JoinColumn(name = "compte_source_id", nullable = false)
    private Compte compteSource;
    @ManyToOne
    @JoinColumn(name = "compte_destinataire_id", nullable = false)
    private Compte compteDestinataire;

    private TypeTransaction typeTransaction;
}
