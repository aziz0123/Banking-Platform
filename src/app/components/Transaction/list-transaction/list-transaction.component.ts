import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../Transaction/service/transaction.service';
import { Transaction } from '../../models/Transaction';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-list-transaction',
  templateUrl: './list-transaction.component.html',
  styleUrls: ['./list-transaction.component.css']
})
export class ListTransactionComponent implements OnInit {
  transactions: Transaction[] = [];
  transactionId: number | null = null;
  errorMessage: string | null = null;
  selectedTransaction: Transaction | null = null;

  constructor(private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions(): void {
    this.transactionService.getAllTransactions().subscribe({
      next: (data) => {
        this.transactions = data;
        console.log('Transactions récupérées:', this.transactions);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des transactions:', err);
      }
    });
  }

  // Rechercher une transaction par ID
  getTransactionById(id: number | null): void {
    if (id !== null) {
      this.transactionService.getTransactionById(id).subscribe({
        next: (data) => {
          this.selectedTransaction = data;
          this.errorMessage = null;
          console.log('Transaction trouvée:', this.selectedTransaction);
        },
        error: (err) => {
          this.selectedTransaction = null;
          this.errorMessage = 'Transaction non trouvée ou erreur lors de la récupération';
          console.error('Erreur:', err);
          
          if (err instanceof HttpErrorResponse) {
            console.error('Erreur HttpErrorResponse:', err);
            console.error('Corps de la réponse:', err.error);
          }
        }
      });
    } else {
      this.errorMessage = 'Veuillez entrer un ID valide';
    }
  }
}
