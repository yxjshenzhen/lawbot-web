import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ModalModule } from 'ngx-bootstrap';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ContractComponent } from './buss/contract/contract.component';

import { UserService } from "./common/service/user.service";
import { ContractService } from './buss/contract.service';

const appRoutes: Routes = [
  { 
    path: '', redirectTo: 'mjjd' ,pathMatch: 'full'
    
  },{
    path: ':caseType' , component: ContractComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ContractComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ModalModule.forRoot(),
    RouterModule.forRoot(
      appRoutes,
      { 
        enableTracing: true,// <-- debugging purposes only
        useHash: true 
      } 
    )
  ],
  providers: [
    UserService,
    ContractService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
