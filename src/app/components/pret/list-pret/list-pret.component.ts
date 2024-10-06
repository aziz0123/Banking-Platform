import { Component } from '@angular/core';
import { DemandePret } from '../../models/DemandePret';
import { StatusDemande } from '../../models/StatusDemande'; // Your enum
import { PretService } from '../service/pret.service';

@Component({
  selector: 'app-list-pret',
  templateUrl: './list-pret.component.html',
  styleUrls: ['./list-pret.component.css']
})
export class ListPretComponent {
  demandesPret: DemandePret[] = [];
  StatusDemande = StatusDemande; // Expose the enum to the template

  constructor(private demandePretService: PretService) { }

  ngOnInit(): void {
    this.loadAllDemandesPret();
  }

  loadAllDemandesPret() {
    this.demandePretService.getAllDemandesPret().subscribe((data) => {
      this.demandesPret = data;
    });
  }

  createDemandePret(email: string, montant: number) {
    this.demandePretService.createDemandePret(email, montant).subscribe(
      (newDemande) => {
        this.demandesPret.push(newDemande);
      },
      (error) => {
        console.error('Error creating demande pret:', error);
      }
    );
  }

  changeStatus(id: number, status: StatusDemande) {
    this.demandePretService.changeStatus(id, status).subscribe();
  }

  updateMontant(id: number, montant: number) {
    this.demandePretService.updateMontant(id, montant).subscribe();
  }

  deleteDemandePret(id: number) {
    this.demandePretService.deleteDemandePret(id).subscribe(() => {
      this.demandesPret = this.demandesPret.filter(d => d.idDemandePret !== id);
    });
  }
}
