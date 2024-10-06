
import { User } from './user';
import { StatusDemande } from './StatusDemande';

export class DemandePret {
  idDemandePret?: number;
  user?: User;
  montant?: number;
  tauxInteret?: number;
  statusDemande?: StatusDemande;
  dateDemande?: Date;
  dateMAJ?: Date;

}

