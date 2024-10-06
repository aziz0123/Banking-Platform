import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/user';
import { Role } from '../../models/Role';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  getUserByUsername(username: string): Observable <User> {
    return this.http.get<any>(`${this.apiUrl}/findUserByUsername/${username}`);
  }

  private apiUrl = 'http://localhost:8081'; // URL de votre backend
  private accessTokenKey = 'access_token';

  constructor(private http: HttpClient) { }

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/findUserByUsername/${email}`);
  }

  updateUser(email: string, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/updateUser/${email}`, user);
  }

  changePassword(email: string, oldPass: string, newPass: string): Observable<void> {
    const request = { email, oldPass, newPass };
    return this.http.put<void>(`${this.apiUrl}/change-password`, request);
  }

  resetPassword(email: string, password: string): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/reset-password/${email}`, null, {
      params: { password }
    });
  }

  enableOrDisableUser(email: string): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/toggelUser`, null, {
      params: { email }
    });
  }

  sendEmail(email: string): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/SendEmail`, {
      params: { email }
    });
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/allusers`);
  }

  getRoleByEmail(email: string): Observable<Role> {
    return this.http.get<Role>(`${this.apiUrl}/getRoleByEmail/${email}`);
  }

  deleteUser(email: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteuser/${email}`);
  }
}