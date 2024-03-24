import { Component, NgModule } from '@angular/core';
import { LandingComponent } from "./landing/landing.component";
import { LoginPageComponent } from './login-page/login-page.component';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    imports: [LandingComponent, LoginPageComponent, RouterModule, CommonModule]
})
export class AppComponent {
  login: boolean = true;
  registeration: boolean = false;
  landing: boolean = false;

  Login() {
    this.login = true;
    this.registeration = false;
    this.landing = false;
  }

  Register() {
    this.login = false;
    this.registeration = true;
    this.landing = false;
  }

  Landing() {
    this.login = false;
    this.registeration = false;
    this.landing = true;
  }
  
  title = 'app';}
