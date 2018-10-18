import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap';
import { NgxEchartsModule } from 'ngx-echarts';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from "./home/home.component";

import { HomeService } from "./home/home.service";
import { UserService } from "./common/service/user.service";
import { MjjdService } from "./buss/mjjd/mjjd.service";
import { MmhtService } from "./buss/mmht/mmht.service";
import { LoadingComponent } from './common/loading/loading.component';
import { MjjdComponent } from './buss/mjjd/mjjd.component';
import { DialogComponent } from './common/component/dialog.component';
import { MmhtComponent } from './buss/mmht/mmht.component';

const appRoutes: Routes = [
  { 
    path: '', 
    redirectTo: 'mjjd' , 
    pathMatch: 'full'
  },{
    path: 'mjjd',
    component: MjjdComponent
  },{
    path: 'mmht',
    component: MmhtComponent
  },{
    path: ':caseType', component: HomeComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoadingComponent,
    MjjdComponent,
    DialogComponent,
    MmhtComponent,
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
    ModalModule.forRoot(),
    NgxEchartsModule
  ],
  providers: [
    HomeService,
    UserService,
    MjjdService,
    MmhtService
  ],
  entryComponents: [
    DialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
