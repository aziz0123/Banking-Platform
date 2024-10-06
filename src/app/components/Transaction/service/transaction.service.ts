import { Injectable } from '@angular/core';
import { Transaction } from '../../models/Transaction';
import { Observable,throwError  } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams,HttpErrorResponse  } from '@angular/common/http';
import { Compte } from '../../models/Compte';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  httpOptions = { headers:new HttpHeaders({'Content-Type':
    'application/json'})}
    constructor(private http: HttpClient) { }  
    private api_url = 'http://localhost:8081/Transaction';


  // Obtenir toutes les transactions
  getAllTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.api_url);
  }



  // Obtenir une transaction par ID
  getTransactionById(id: number): Observable<Transaction> {
    return this.http.get<Transaction>(`${this.api_url}/${id}`);
  }

  // Créer une nouvelle transaction
  createTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(this.api_url, transaction);
  }

  // Mettre à jour une transaction existante
  updateTransaction(id: number, transaction: Transaction): Observable<Transaction> {
    return this.http.put<Transaction>(`${this.api_url}/${id}`, transaction);
  }

  // Supprimer une transaction par ID
  deleteTransaction(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api_url}/${id}`);
  }

  // Effectuer une transaction
  performTransaction(numsourceCompte: number, numdestinationCompte: number, montant: number, typeTransaction: string): Observable<Transaction> {
    const params = { numdestinationCompte: numdestinationCompte.toString(), montant: montant.toString(), typeTransaction };
    return this.http.post<Transaction>(`${this.api_url}/performTransaction/${numsourceCompte}`, {}, { params });
  }
}
