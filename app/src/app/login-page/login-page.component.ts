import { Component, Output, EventEmitter } from '@angular/core';
import { RegisterationComponent } from '../registeration/registeration.component';
@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [RegisterationComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  @Output("Register") Register: EventEmitter<any> = new EventEmitter();
  @Output("Landing") Landing: EventEmitter<any> = new EventEmitter();
  title = 'login';

  emitToggleEvent() {
    this.Register.emit();
  }
  kirjaudu(){
    this.Landing.emit();
  }
}
