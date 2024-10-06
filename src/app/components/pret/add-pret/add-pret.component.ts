import { Component } from '@angular/core';
import { DemandePret } from '../../models/DemandePret';
import { StatusDemande } from '../../models/StatusDemande'; // Your enum
import { PretService } from '../service/pret.service';

@Component({
  selector: 'app-add-pret',
  templateUrl: './add-pret.component.html',
  styleUrls: ['./add-pret.component.css']
})
export class AddPretComponent {
  demandesPret: DemandePret[] = [];
  StatusDemande = StatusDemande; // Expose the enum to the template
  email: string = '';
  montant: number = 0;
  demandePret?: DemandePret;
  errorMessage: string = '';
  constructor(private demandePretService: PretService) { }

  ngOnInit(): void {
    this.loadAllDemandesPret();
  }

  loadAllDemandesPret() {
    this.demandePretService.getAllDemandesPret().subscribe((data) => {
      this.demandesPret = data;
    });
  }

  onSubmit() {
    this.demandePretService.createDemandePret(this.email, this.montant)
      .subscribe({
        next: (response) => {
          this.demandePret = response;
          console.log('Demande de prêt créée avec succès', response);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la création de la demande de prêt';
          console.error(error);
        }
      });
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
