import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login/service/login.service';
import { LoginResponse } from '../../models/loginResponse';
import { RegisterDto } from '../../models/RegisterDto';
import { LoginRequest } from '../../models/loginRequest';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  onSubmit() {
    // Créer l'objet LoginRequest avec les informations d'identification de l'utilisateur
    const loginRequest: LoginRequest = {
      email: this.email,
      password: this.password
    };
    
    // Appel du service d'authentification
    this.loginService.login(loginRequest).subscribe(
      (response: LoginResponse) => {
        console.log('Login successful!', response.accessToken);
        
        // Stocker le token d'accès, par exemple dans le localStorage
        localStorage.setItem('accessToken', response.accessToken);

        // Rediriger vers la page d'accueil après connexion réussie
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Login failed', error);
        this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos informations.';
      }
    );
  }
}
