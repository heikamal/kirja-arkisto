import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService],
  templateUrl: './admin.component.html'
})

export class AdminComponent {

  chosen_book_id: any;
  remove_book_id: any;
  remove_series_id: any;
  book_form: FormGroup;
  series_form: FormGroup;
  book_data: any;
  single_book_data: any;
  series_data: any;
  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService

  ) {
    this.book_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      kirjailija: ['', Validators.maxLength(45)],
      julkaisuVuosi: ['', Validators.max(2030)],
      sarja: [''],
      kuvaus: ['', Validators.maxLength(255)],
      jarjestysNro: ['']
    });

    this.series_form = fb.group({
      title: ['', Validators.maxLength(45)],
      kustantaja: ['', Validators.maxLength(45)],
      kuvaus: ['', Validators.maxLength(255)],
      luokittelu: ['', Validators.maxLength(45)],
    });
  }
  remove_book() {
    this.dataService.remove_book(this.remove_book_id).subscribe(() => {
      this.show_books();
    }, error => {
      console.error('Error occurred while removing book:', error);
    });
  }
  show_books() {
    this.dataService.get_books().subscribe(response => {
      this.book_data = response;
      console.log('Fetched Data:', this.book_data);
    })
  }
  show_book() {
    this.dataService.get_book(this.chosen_book_id).subscribe(response => {
      this.single_book_data = response;
      console.log('Fetched Data:', this.single_book_data);
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
  remove_series() {
    this.dataService.remove_series(this.remove_series_id).subscribe(() => {
      this.show_series();
    }, error => {
      console.error('Error occurred while removing book:', error);
    });
  }
}
