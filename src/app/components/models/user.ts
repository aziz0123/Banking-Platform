import { Role } from './Role'; // Assurez-vous que l'énumération Role est définie
import { Compte } from './Compte'; // Assurez-vous que la classe Compte est définie
import { DemandePret } from './DemandePret'; // Assurez-vous que la classe DemandePret est définie

export class User {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  cin?: string;
  password?: string;
  dateN?: Date; // Utiliser Date pour représenter LocalDate
  telephon?: string;
  role?: Role = Role.USER;
  comptes?: Compte[];
  prets?: DemandePret[];
  updatedAt?: Date;
  createdAt?: Date;
  enabled1?: boolean = false;
  lastLogin?: Date;
  verificationToken?: string;

  
}
