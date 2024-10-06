import { Component, OnInit } from '@angular/core';
import { CompteService } from '../service/compte.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Compte } from '../../models/Compte';

@Component({
  selector: 'app-add-comptes',
  templateUrl: './add-comptes.component.html',
  styleUrls: ['./add-comptes.component.css']
})
export class AddComptesComponent implements OnInit {
  compteForm!: FormGroup;
  comptes: Compte[] = [];
  selectedCompteId?: number;
  typeCompteOptions = [
    { value: 'CHEQUE', label: 'Cheque' },
    { value: 'EPARGNE', label: 'Epargne' }
  ];
  constructor(
    private compteService: CompteService,
    private formBuilder: FormBuilder,
    private router: Router
    
  ) { }



  ngOnInit(): void {
    this.compteForm = this.formBuilder.group({
      numeroCompte: ['', Validators.required],
      solde: ['', Validators.required],
      typeCompte: ['', Validators.required],
      dateOuverture: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() { return this.compteForm.controls; }

  createCompte(): void {
    if (this.compteForm.valid) {
      const compteData = {
        numeroCompte: this.compteForm.get('numeroCompte')!.value,
        solde: this.compteForm.get('solde')!.value,
        typeCompte: this.compteForm.get('typeCompte')!.value,
        dateOuverture: this.compteForm.get('dateOuverture')!.value
      };
      const email = this.compteForm.get('email')!.value;
  
      console.log('Compte Data:', compteData); // Debugging log
      console.log('Email:', email); // Debugging log
    
      this.compteService.createCompte(compteData, email).subscribe(
        (response: Compte) => {
          console.log('Compte added successfully', response);
          this.router.navigate(['/comptes']);
        },
        (error) => {
          console.log('Error while adding compte: ', error);
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }

  
}
