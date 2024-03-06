import { Component, Output, EventEmitter, Inject } from '@angular/core';
import { RegisterationComponent } from '../registeration/registeration.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DataService } from '../data.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [RegisterationComponent, ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService],
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent {
  login_form: FormGroup;

  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService
    
  ) {
    this.login_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      salasana: ['', Validators.maxLength(45)],
    });
  }

  @Output("Register") Register: EventEmitter<any> = new EventEmitter();
  @Output("Landing") Landing: EventEmitter<any> = new EventEmitter();
  title = 'login';

  login() {
    this.dataService.login_user(this.login_form
  .value).subscribe(response => {
      console.log('Response:', response);
    })
    this.Landing.emit();
  }

  emitToggleEvent() {
    this.Register.emit();
  }
}
