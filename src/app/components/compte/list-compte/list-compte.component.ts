import { Component } from '@angular/core';
import { CompteService } from '../service/compte.service';
import { Compte } from 'src/app/components/models/Compte'; // Adjust the path based on your structure
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-list-compte',
  templateUrl: './list-compte.component.html',
  styleUrls: ['./list-compte.component.css']
})
export class ListCompteComponent {
  comptes: Compte[] = [];
  compte: any = {};
  searchTerm: string = '';
  email: string = ''; // Bind to the input field in the template
  emailSearched: boolean = false; // To track if a search has been made
  selectedCompteId?: number;
  constructor(
    private compteService: CompteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllComptes();
  }

  deleteCompte(compte: Compte): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce compte ?')) {
      this.compteService.deleteCompte(compte.id!).subscribe(() => {
        console.log('Compte supprimé avec succès');
        this.getAllComptes(); // Rafraîchir la liste des comptes après suppression
      }, (error: HttpErrorResponse) => {
        console.log('Erreur lors de la suppression du compte : ', error);
      });
    }
    
  }

  getAllComptes(): void {
    this.compteService.getAllComptes().subscribe(
      (data: Compte[]) => {
        console.log('Comptes récupérés : ', data);
        this.comptes = data;
      },
      (error: HttpErrorResponse) => {
        console.log('Erreur lors de la récupération des comptes : ', error);
      }
    );
  }

  getComptesByUserEmail(): void {
    this.emailSearched = true;
    if (this.email) {
      this.compteService.getComptesByUserEmail(this.email).subscribe(
        (data: Compte[]) => {
          this.comptes = data;
          console.log('Comptes loaded', this.comptes);
        },
        (error) => {
          console.error('Error loading comptes', error);
          this.comptes = []; // Reset to empty if there's an error
        }
      );
    } else {
      console.warn('Email is required');
      this.comptes = []; // Reset to empty if no email is provided
    }
  }
  downloadReleve(compteId?: number): void {
    if (compteId) {
      this.compteService.generateReleve(compteId).subscribe(
        (response: Blob) => {
          const blob = new Blob([response], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `releve_compte_${compteId}.pdf`;
          a.click();
          window.URL.revokeObjectURL(url);
        },
        error => {
          console.error('Erreur lors du téléchargement du relevé', error);
        }
      );
    } else {
      console.warn('Invalid account ID');
    }
  }

}
