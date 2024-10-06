import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/template/header/header.component';
import { HomeComponent } from './components/template/home/home.component';
import { FooterComponent } from './components/template/footer/footer.component';
import { LoginComponent } from './components/template/login/login.component';
import { AdminComponent } from './admin/admin.component';
import { AddComptesComponent } from './components/compte/add-comptes/add-comptes.component';
import { ListUserComponent } from './components/user/list-user/list-user.component';
import { ListCompteComponent } from './components/compte/list-compte/list-compte.component';
import { DetailCompteComponent } from './components/compte/detail-compte/detail-compte.component'; // Assurez-vous d'importer ce module
import { FooterAdminComponent } from './admin/footer-admin/footer-admin.component';
import { HeaderAdminComponent } from './admin/header-admin/header-admin.component'; // Assurez-vous d'importer ce module
import { AddTransactionComponent } from './components/Transaction/add-transaction/add-transaction.component';
import { ListTransactionComponent } from './components/Transaction/list-transaction/list-transaction.component';
import { AddPretComponent } from './components/pret/add-pret/add-pret.component';
import { ListPretComponent } from './components/pret/list-pret/list-pret.component';
import { AddUserComponent } from './components/user/add-user/add-user.component';
import { RegisterComponent } from './components/template/login/register/register.component';
import { AuthPageComponent } from './components/template/login/auth-page/auth-page.component';

const routes: Routes = [
  //{ path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'header', component: HeaderComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'add-compte', component: AddComptesComponent },
  { path: 'list-user', component: ListUserComponent },
  { path: 'list-compte', component: ListCompteComponent },
  { path: 'detailCompte/:id', component: DetailCompteComponent },
  { path: 'header', component: HeaderComponent },
  { path: 'footer', component: FooterComponent },
  { path: 'footeradmin', component: FooterAdminComponent },
  { path: 'headeradmin', component: HeaderAdminComponent },
  { path: 'add-transaction', component: AddTransactionComponent },
  { path: 'list-transaction', component: ListTransactionComponent },
  { path: 'add-pret', component: AddPretComponent },
  { path: 'list-pret', component: ListPretComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'auth-page', component: AuthPageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
