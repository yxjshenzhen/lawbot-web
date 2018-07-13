import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from "./home/home.component";

import { HomeService } from "./home/home.service";
import { UserService } from "./common/service/user.service";
import { RecoService } from "./buss/reco.service";
import { LoadingComponent } from './common/loading/loading.component';
import { RecoComponent } from './buss/reco/reco.component';

const appRoutes: Routes = [
  { 
    path: '', 
    component: HomeComponent,
    children: [
      {
        path: '' , redirectTo: 'mjjd', pathMatch: 'full'
      },{
        path: 'mjjd', component: RecoComponent,
      }
      // ,{
      //   path: 'mmht', component: RecoComponent,
      // },
      // ,{
      //   path: 'jrtz', component: RecoComponent,
      // }
      // ,{
      //   path: 'zscq', component: RecoComponent,
      // }
      // ,{
      //   path: 'sszc', component: RecoComponent,
      // },
      // ,{
      //   path: 'fdc', component: RecoComponent,
      // }
      // ,{
      //   path: 'hswl', component: RecoComponent,
      // }
    ]
  },
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoadingComponent,
    RecoComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(
      appRoutes,
      { 
        enableTracing: true,// <-- debugging purposes only
        useHash: true 
      } 
    ),
    ModalModule.forRoot()
  ],
  providers: [
    HomeService,
    UserService,
    RecoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
