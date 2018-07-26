import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ModalModule } from 'ngx-bootstrap';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AwardComponent } from './buss/award/award.component';

import { UserService } from "./common/service/user.service";
import { AwardService } from './buss/award.service';

const appRoutes: Routes = [
  { 
    path: '', redirectTo: 'mjjd' ,pathMatch: 'full'
    
  },{
    path: ':caseType' , component: AwardComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AwardComponent
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
    AwardService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
