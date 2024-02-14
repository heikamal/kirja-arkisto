import { Component, Output, EventEmitter } from '@angular/core';
@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  @Output("kirjautuminen") kirjautuminen: EventEmitter<any> = new EventEmitter();
  title = 'login';

  emitToggleEvent() {
    this.kirjautuminen.emit();
  }
}
