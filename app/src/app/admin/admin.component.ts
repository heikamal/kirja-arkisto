import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  providers: [DataService],
  templateUrl: './admin.component.html'
})

export class AdminComponent {

  book_form: FormGroup;
  series_form: FormGroup;
  book_data: any;
  series_data: any;
  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService

  ) {
    this.book_form = fb.group({
      title: ['', Validators.maxLength(45)],
      author: ['', Validators.maxLength(45)],
      year: ['', Validators.max(2030)],
      description: ['', Validators.maxLength(255)],
      serial: [''],
      series_id: ['']
    });

    this.series_form = fb.group({
      title: ['', Validators.maxLength(45)],
      kustantaja: ['', Validators.maxLength(45)],
      kuvaus: ['', Validators.maxLength(255)],
      luokittelu: ['', Validators.maxLength(45)],
    });
  }
  show_book() {
    this.dataService.get_book().subscribe(response => {
      this.book_data = response;
      console.log('Fetched Data:', this.book_data);
    })
  }
  submit_book() {
    if (this.book_form.valid) {
      this.dataService.post_book(this.book_form.value).subscribe(response => {
        console.log('Response:', response);
      })
    } (error: HttpErrorResponse) => {
      console.error('Error:', error);
    }
  }
  show_series() {
    this.dataService.get_series().subscribe(response => {
      this.series_data = response;
      console.log('Fetched Data:', this.series_data);
    })
  }
  submit_series() {
    if (this.series_form.valid) {
      this.dataService.post_series(this.series_form.value).subscribe(response => {
        console.log('Response:', response);
      })
    } (error: HttpErrorResponse) => {
      console.error('Error:', error);
    }
  }
}
