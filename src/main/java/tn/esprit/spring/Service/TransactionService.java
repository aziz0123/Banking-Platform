package tn.esprit.spring.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.Entity.Transaction;
import tn.esprit.spring.Entity.TypeTransaction;
import tn.esprit.spring.Repository.CompteRepository;
import tn.esprit.spring.Repository.TransactionRepository;
import tn.esprit.spring.errors.ResourceNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class TransactionService implements ITransactionService{

        @Autowired
        private TransactionRepository transactionRepository;
        @Autowired
        private CompteRepository compteRepository;

        // CRUD operations

        public List<Transaction> getAllTransactions() {
            return transactionRepository.findAll();
        }

        public Optional<Transaction> getTransactionById(Long id) {
            return transactionRepository.findById(id);
        }

        public Transaction createTransaction(Transaction transaction) {
            return transactionRepository.save(transaction);
        }

        public Transaction updateTransaction(Long id, Transaction transactionDetails) {
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + id));

            transaction.setMontant(transactionDetails.getMontant());
            transaction.setDateDeTransaction(transactionDetails.getDateDeTransaction());
            transaction.setCompteSource(transactionDetails.getCompteSource());
            transaction.setCompteDestinataire(transactionDetails.getCompteDestinataire());
            transaction.setTypeTransaction(transactionDetails.getTypeTransaction());

            return transactionRepository.save(transaction);
        }

        public void deleteTransaction(Long id) {
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + id));
            transactionRepository.delete(transaction);
        }

        // Service to perform a transaction
        public Transaction effectuerTransaction(Long numsourceCompte, Long numdestinationCompte, double montant, TypeTransaction typetransaction) {
            // Récupérer les comptes source et destination
            Compte compteSource = compteRepository.findByNumeroCompte(numsourceCompte)
                    .orElseThrow(() -> new NoSuchElementException("Compte source introuvable"));
            Compte compteDestination = compteRepository.findByNumeroCompte(numdestinationCompte)
                    .orElseThrow(() -> new NoSuchElementException("Compte destination introuvable"));

            // Vérifier si le compte source a suffisamment de solde
            if (compteSource.getSolde() < montant) {
                throw new RuntimeException("Solde insuffisant");
            }

            // Débiter le compte source
            compteSource.setSolde(compteSource.getSolde() - montant);

            // Créditez le compte destination
            compteDestination.setSolde(compteDestination.getSolde() + montant);

            // Créer et sauvegarder la transaction
            Transaction transaction = new Transaction();
            transaction.setCompteSource(compteSource);
            transaction.setCompteDestinataire(compteDestination);
            transaction.setMontant(montant);
            transaction.setDateDeTransaction(new Date());
            transaction.setTypeTransaction(typetransaction);

            // Ajouter la transaction à la liste des transactions du compte source
            compteSource.getTransactions().add(transaction);
            compteDestination.getTransactions().add(transaction);
            // Sauvegarder les comptes et la transaction
            compteRepository.save(compteSource);
            compteRepository.save(compteDestination);

            return transactionRepository.save(transaction);
        }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }
    }

