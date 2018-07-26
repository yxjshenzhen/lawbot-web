import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { UserService } from "./common/service/user.service";

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from "./home/home.component";
import { ProductComponent } from './buss/product/product.component';
import { AdjuComponent } from './buss/adju/adju.component';
import { AboutComponent } from './buss/about/about.component';

const appRoutes: Routes = [
  {
    path: 'product', component: ProductComponent
  },
  {
    path: 'adju', component: AdjuComponent
  },
  {
    path: 'about', component: AboutComponent
  },
  { path: '**', component: HomeComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ProductComponent,
    AdjuComponent,
    AboutComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { 
        enableTracing: true,// <-- debugging purposes only
        useHash: true 
      } 
    ),
  ],
  providers: [
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
