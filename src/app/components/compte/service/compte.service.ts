import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Compte } from '../../models/Compte';

@Injectable({
  providedIn: 'root',
})
export class CompteService {
  private api_url = 'http://localhost:8081';

  httpOptions = { headers:new HttpHeaders({'Content-Type':
    'application/json'})}
    constructor(private http: HttpClient) { }

  // Récupérer un compte par ID
  getCompteById(id: number): Observable<Compte> {
    return this.http.get<Compte>(`${this.api_url}/Compte/getCompteById/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  // Récupérer un compte par email

  getComptesByUserEmail(email: string): Observable<Compte[]> {
    return this.http.get<Compte[]>(`${this.api_url}/Compte/userAcounts`, {
      params: new HttpParams().set('email', email)
    }).pipe(
      catchError(this.handleError)
    );
  }

  // Récupérer tous les comptes

  getAllComptes(): Observable<Compte[]> {
    return this.http.get<any[]>(`${this.api_url}/Compte/getAllComptes`);
  }

  createCompte(compte: Compte, email: string): Observable<Compte> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const url = `${this.api_url}/Compte/createCompte?email=${encodeURIComponent(email)}`;
    return this.http.post<Compte>(url, compte, { headers });
  }
  
  // Mettre à jour un compte avec FormData (multipart/form-data)
  updateCompte(id: number, form: FormData): Observable<any> {
    return this.http.put<any>(`${this.api_url}/Compte/updateCompte/${id}`, form)
      .pipe(
        catchError(this.handleError)
      );
  }

  // Supprimer un compte par ID
  deleteCompte(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api_url}/Compte/deleteCompte/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  // Récupérer le solde d'un compte par ID
  getSolde(id: number): Observable<number> {
    return this.http.get<number>(`${this.api_url}/Compte/getSolde/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  // Mettre à jour le solde d'un compte
  updateSolde(id: number, newSolde: number): Observable<Compte> {
    return this.http.put<Compte>(`${this.api_url}/Compte/updateSolde/${id}`, { solde: newSolde }, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  // Gestion des erreurs
  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error(error.message || 'Server Error'));
  }

  generateReleve(compteId: number): Observable<Blob> {
    const url = `${this.api_url}/Releve/generate/${compteId}`;
    return this.http.get(url, {
      responseType: 'blob',
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }
}
