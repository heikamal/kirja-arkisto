import { Component, NgModule } from '@angular/core';
import { LandingComponent } from "./landing/landing.component";
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterationComponent } from './registeration/registeration.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    imports: [LandingComponent, LoginPageComponent, RegisterationComponent, RouterModule, CommonModule]
})
export class AppComponent {
  login: boolean = false;

  kirjautuminen() {
   this.login = true;
  }
  
  title = 'app';}
