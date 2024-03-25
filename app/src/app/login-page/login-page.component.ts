import { Component, Output, EventEmitter, Inject, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { DataService } from '../data.service';
import { CookieService } from 'ngx-cookie-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService, CookieService],
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent{
  login_form: FormGroup;

  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService, private cookieService: CookieService,
    private dialog: MatDialog
  ) {
    this.login_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      salasana: ['', Validators.maxLength(45)],
    });
  }

  ngOnInit() {
    const accessTokenExists = this.cookieService.check("accessToken");
    if (accessTokenExists) {
      setTimeout(() => {
        this.emitLanding();
      });
    }
  }

  @Output("Landing") Landing: EventEmitter<any> = new EventEmitter();
  title = 'login';

  login() {
    this.dataService.login_user(this.login_form.value).subscribe(
      (response) => {
        console.log('Response:', response);
        const jsonStr: string = JSON.stringify(response);
        const jsonObject: any = JSON.parse(jsonStr);
        const accessToken: string = jsonObject.accessToken;
        const tokenType: string = jsonObject.tokenType;
        const nimi: string = jsonObject.nimi;
        const type: string = jsonObject.roolit;
        console.log(accessToken);
        this.cookieService.set("accessToken",  accessToken);
        this.cookieService.set("user", nimi);
        this.cookieService.set("roles", type);
        this.openSuccessDialog();
        this.emitLanding();
      },
      (error) => {
        console.error('Login failed:', error);
        this.openErrorDialog();
      }
    );
  }

  emitLanding() {
    this.Landing.emit();
  }

  openErrorDialog(): void {
    const dialogRef = this.dialog.open(ErrorDialogComponent, {
      width: '250px',
      data: { message: 'Käyttäjänimi tai salasana on väärin. Tarkista syötteet.' }
    });
  }

  openSuccessDialog(): void {
  }
}
