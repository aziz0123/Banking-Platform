package tn.esprit.spring.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.Entity.Transaction;
import tn.esprit.spring.Entity.TypeTransaction;
import tn.esprit.spring.Service.TransactionService;
import tn.esprit.spring.errors.ResourceNotFoundException;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("Transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for this id :: " + id));
        return ResponseEntity.ok(transaction);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to perform a transaction
    @PostMapping("/performTransaction/{numsourceCompte}")
    public Transaction performTransaction(@PathVariable("numsourceCompte") Long numsourceCompte,
                                          @RequestParam Long numdestinationCompte,
                                          @RequestParam double montant,
                                          @RequestParam TypeTransaction typeTransaction) {
        return transactionService.effectuerTransaction(numsourceCompte, numdestinationCompte, montant,typeTransaction);
    }
}
