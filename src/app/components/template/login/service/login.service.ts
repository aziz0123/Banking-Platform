import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../../../models/loginRequest';
import { LoginResponse } from '../../../models/loginResponse';
import { RegisterDto } from '../../../models/RegisterDto';
import { ChangePasswordRequest } from '../../../models/ChangePasswordRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl ='http://localhost:8081';

  constructor(private http: HttpClient) {}

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, loginRequest);
  }

  register(registerDto: RegisterDto): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/auth/register`, registerDto);
  }

  verifyOtp(email: string, otp: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/verify-otp`, { email, otp });
  }

  generateOtp(email: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/generate-otp`, null, { params: { email } });
  }

  forgotPassword(email: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/forgot-password`, null, { params: { email } });
  }

  resetPassword(token: string, newPassword: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/reset-password`, null, {
      params: { token, newPassword }
    });
  }

  changePassword(ChangePasswordRequest: ChangePasswordRequest): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/auth/change-password`, ChangePasswordRequest);
  }
}
