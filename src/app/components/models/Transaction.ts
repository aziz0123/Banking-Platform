import { Compte } from './Compte';
import { TypeTransaction } from './TypeTransaction';

export class Transaction {
  idTransaction?: number;
  montant?: number;
  dateDeTransaction?: Date;
  compteSource?: Compte;
  compteDestinataire!: Compte;
  typeTransaction!: TypeTransaction;
}
