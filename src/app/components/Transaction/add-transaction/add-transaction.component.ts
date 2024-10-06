import { Component } from '@angular/core';
import { Transaction } from '../../models/Transaction';
import { TransactionService } from '../service/transaction.service';
import { TypeTransaction } from '../../models/TypeTransaction';
import { Compte } from '../../models/Compte';
import { Router } from '@angular/router';
import { CompteService } from '../../compte/service/compte.service';


@Component({
  selector: 'app-add-transaction',
  templateUrl: './add-transaction.component.html',
  styleUrls: ['./add-transaction.component.css']
})

export class AddTransactionComponent {
  transaction: Transaction = new Transaction();
  comptes: Compte[] = [];  // Pour stocker les comptes disponibles
  typeTransactions = TypeTransaction; // Enum pour les options de type de transaction
  

  constructor(private transactionService: TransactionService,
    private compteService: CompteService, // Ajoute cette ligne
    private router: Router) { }

    ngOnInit(): void {
      this.getAllComptes(); // Charge les comptes lors de l'initialisation
    }
  
    getAllComptes(): void {
      this.compteService.getAllComptes().subscribe({
        next: (data) => this.comptes = data,
        error: (error) => console.error('Erreur lors du chargement des comptes', error)
      });
    }

    createTransaction(): void {
      if (this.transaction.compteDestinataire && this.transaction.montant && this.transaction.typeTransaction) {
        this.transactionService.performTransaction(
          this.transaction.compteSource!.numeroCompte!,
          this.transaction.compteDestinataire.numeroCompte!,
          this.transaction.montant!,
          this.transaction.typeTransaction
        ).subscribe({
          next: (data) => {
            console.log('Transaction effectuée avec succès', data);
            this.router.navigate(['/transactions']);  // Rediriger vers la liste des transactions après la création
          },
          error: (error) => console.error('Erreur lors de la création de la transaction', error)
        });
      } else {
        console.error('Veuillez remplir tous les champs nécessaires');
      }
    }
}
