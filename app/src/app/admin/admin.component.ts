import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data-service.service';
import { Data } from '@angular/router';
import { response } from 'express';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  providers: [DataService],
  templateUrl: './admin.component.html'
})

export class AdminComponent {

  form: FormGroup;
  book_data: any;
  constructor(@Inject(FormBuilder) fb: FormBuilder,
  private dataService: DataService

  ) {
    this.form = fb.group({
        title: ['', Validators.maxLength(45)],
        author: ['', Validators.maxLength(45)],
        year:['', Validators.max(2030)],
        description:['', Validators.maxLength(255)],
        serial:['']
    });
  }
  show_book(){
    this.dataService.getBook().subscribe(response => {
      this.book_data = response;
      console.log('Fetched Data:', this.book_data);
    })
  }
  submit_book(){
    this.dataService.postData(this.form.value).subscribe(response => {
      console.log('Response:', response);
    })
  }
}
