import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormGroup, FormsModule } from '@angular/forms'; // Importez FormsModule

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/template/header/header.component';
import { HomeComponent } from './components/template/home/home.component';
import { FooterComponent } from './components/template/footer/footer.component';
import { LoginComponent } from './components/template/login/login.component';
import { AdminComponent } from './admin/admin.component';
import { IonicModule } from '@ionic/angular';
import { ListCompteComponent } from './components/compte/list-compte/list-compte.component';
import { AddComptesComponent } from './components/compte/add-comptes/add-comptes.component';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AddUserComponent } from './components/user/add-user/add-user.component';
import { ListUserComponent } from './components/user/list-user/list-user.component';
import { DeleteUserComponent } from './components/user/delete-user/delete-user.component';
import { DetailCompteComponent } from './components/compte/detail-compte/detail-compte.component';
import { FooterAdminComponent } from './admin/footer-admin/footer-admin.component';
import { HomeAdminComponent } from './admin/home-admin/home-admin.component';
import { HeaderAdminComponent } from './admin/header-admin/header-admin.component';
import { AddTransactionComponent } from './components/Transaction/add-transaction/add-transaction.component';
import { ListTransactionComponent } from './components/Transaction/list-transaction/list-transaction.component';
import { AddPretComponent } from './components/pret/add-pret/add-pret.component';
import { ListPretComponent } from './components/pret/list-pret/list-pret.component';
import { RegisterComponent } from './components/template/login/register/register.component';
import { AuthPageComponent } from './components/template/login/auth-page/auth-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    FooterComponent,
    LoginComponent,
    AdminComponent,
    ListCompteComponent,
    AddComptesComponent,
    AddUserComponent,
    ListUserComponent,
    DeleteUserComponent,
    DetailCompteComponent,
    FooterAdminComponent,
    HomeAdminComponent,
    HeaderAdminComponent,
    AddTransactionComponent,
    ListTransactionComponent,
    AddPretComponent,
    ListPretComponent,
    RegisterComponent,
    AuthPageComponent,
  ],
  imports: [
    IonicModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule// Ajoutez FormsModule ici
  ],
  providers: [],
  bootstrap: [AppComponent]
  
})
export class AppModule { }
