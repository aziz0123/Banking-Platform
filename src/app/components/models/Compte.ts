import { User } from "./user";
import { TypeCompte } from "./TypeCompte";
import { Transaction } from "./Transaction";

export class Compte {
    id?: number;
    numeroCompte?: number;
    solde?: number;
    proprietaire?: User;
    typeCompte?: TypeCompte;
    dateOuverture?: Date;
    transactions?: Transaction[];

  }
  
  