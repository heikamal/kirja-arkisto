import { Component } from '@angular/core';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterationComponent } from './registeration/registeration.component';
import { LandingComponent } from './landing/landing.component';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LoginPageComponent, RegisterationComponent, LandingComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'app';
}
