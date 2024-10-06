import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DemandePret } from '../../models/DemandePret';
import { StatusDemande } from '../../models/StatusDemande'; // Your enum

@Injectable({
  providedIn: 'root'
})
export class PretService {

  private apiUrl = 'http://localhost:8081/DemandePret'; // Adjust if necessary

  constructor(private http: HttpClient) { }

  
  createDemandePret(email: string, montant: number): Observable<DemandePret> {
    const params = new HttpParams()
      .set('email', email)
      .set('montant', montant.toString());

    return this.http.post<DemandePret>(`${this.apiUrl}/createDemandePret`, null, { params });
  }

  changeStatus(id: number, status: StatusDemande): Observable<DemandePret> {
    return this.http.put<DemandePret>(`${this.apiUrl}/statusDemandePret/${id}`, null, { params: { status } });
  }

  updateMontant(id: number, montant: number): Observable<DemandePret> {
    return this.http.put<DemandePret>(`${this.apiUrl}/update-montantPret/${id}`, null, { params: { montant: montant.toString() } });
  }

  getAllDemandesPret(): Observable<DemandePret[]> {
    return this.http.get<DemandePret[]>(`${this.apiUrl}/allDemandePret`);
  }

  deleteDemandePret(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteDemandePret/${id}`);
  }
}
