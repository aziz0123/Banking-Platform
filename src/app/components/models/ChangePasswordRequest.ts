export class ChangePasswordRequest {
    oldPass: string;
    newPass: string;
    email: string;
  
    constructor(oldPass: string, newPass: string, email: string) {
      this.oldPass = oldPass;
      this.newPass = newPass;
      this.email = email;
    }
  }