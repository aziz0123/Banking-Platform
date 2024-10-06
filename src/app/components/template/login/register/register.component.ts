import { Component } from '@angular/core';
import { RegisterDto } from '../../../models/RegisterDto';
import { Role } from '../../../models/Role';
import { LoginRequest } from '../../../models/loginRequest';
import {LoginService} from '../../login/service/login.service';
import { Router } from '@angular/router'; // Pour la navigation après l'enregistrement
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerDto: RegisterDto = {
    email: '',
    nom: '',
    prenom: '',
    cin: '',
    password: '',
    telephone: '',
    dateN: new Date(), // Initialise avec la date actuelle ou une valeur par défaut
    role: Role.USER // Initialise avec une valeur par défaut de Role
  };
    roles: string[] = ['ADMIN', 'USER']; // Exemple de rôles disponibles
    registrationError: string | null = null;
  
    constructor(private loginService: LoginService, private router: Router) {}
  
    onSubmit(registerForm: NgForm): void {
      if (registerForm.valid) {
        this.loginService.register(this.registerDto).subscribe({
          next: (response) => {
            console.log('User registered successfully:', response);
            this.router.navigate(['/login']); // Redirigez l'utilisateur vers la page de connexion
          },
          error: (err) => {
            console.error('Registration error:', err);
            this.registrationError = 'Failed to register. Please try again.';
          }
        });
      } else {
        this.registrationError = 'Please fill out all required fields.';
      }
    }
}
