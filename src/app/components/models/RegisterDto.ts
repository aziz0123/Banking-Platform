import { Role } from './Role'; // assuming you have a Role model
export class RegisterDto {
  email: string;
  nom: string;
  prenom: string;
  cin: string;
  password: string;
  telephone: string;
  dateN: Date;
  role: Role;

  constructor(
    email: string,
    nom: string,
    prenom: string,
    cin: string,
    password: string,
    telephone: string,
    dateN: Date,
    role: Role
  ) {
    this.email = email;
    this.nom = nom;
    this.prenom = prenom;
    this.cin = cin;
    this.password = password;
    this.telephone = telephone;
    this.dateN = dateN;
    this.role = role;
  }
}