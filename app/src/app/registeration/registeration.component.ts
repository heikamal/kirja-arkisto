import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';

@Component({
  selector: 'app-registeration',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService],
  templateUrl: './registeration.component.html'
})
export class RegisterationComponent {
  registeration_form: FormGroup;
  roles: string[] = ["admin", "user"]

  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService
    
  ) {
    this.registeration_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      salasana: ['', Validators.maxLength(45)],
      rooli: [this.roles]
    });
  }

  register_user() {
    this.dataService.register_user(this.registeration_form
  .value).subscribe(response => {
      console.log('Response:', response);
    })
  }
}
