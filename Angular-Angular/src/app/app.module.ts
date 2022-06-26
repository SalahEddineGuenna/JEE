import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CutomersComponent } from './cutomers/cutomers.component';
import { AccountsComponent } from './accounts/accounts.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { NewCustomerComponent } from './new-customer/new-customer.component';
// @ts-ignore
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CutomersComponent,
    AccountsComponent,
    NewCustomerComponent,
    CustomerAccountsComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }