import { User } from './user';

export class PasswordResetToken {
  id?: number;
  token?: string;
  user?: User;
  expiryDate?: Date;
}