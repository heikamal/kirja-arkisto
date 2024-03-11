import { Component, Output, EventEmitter, Inject } from '@angular/core';
import { RegisterationComponent } from '../registeration/registeration.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DataService } from '../data.service';
import { CookieService } from 'ngx-cookie-service';


@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [RegisterationComponent, ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService, CookieService],
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent {
  login_form: FormGroup;


  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService, private cookieService: CookieService
    
  ) {
    this.login_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      salasana: ['', Validators.maxLength(45)],
    })
  }

  @Output("Register") Register: EventEmitter<any> = new EventEmitter();
  @Output("Landing") Landing: EventEmitter<any> = new EventEmitter();
  title = 'login';

  login() {
    this.dataService.login_user(this.login_form
  .value).subscribe(response => {
      console.log('Response:', response);
      this.cookieService.set("Response", JSON.stringify(response));
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      const accessToken: string = jsonObject.accessToken;
      const tokenType: string = jsonObject.tokenType;
      console.log(accessToken); // This will print the access token
      this.cookieService.set("accessToken",  tokenType + " " + accessToken);
    })
    this.Landing.emit();
  }

  emitToggleEvent() {
    this.Register.emit();
  }
}
