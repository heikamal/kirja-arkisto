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

  remove_book_id: any;
  book_form: FormGroup;
  series_form: FormGroup;
  book_data: any;
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
    // Call the service method to remove the book by its ID
    this.dataService.remove_book(this.remove_book_id).subscribe(() => {
      // Assuming the removal was successful, you may want to update the fetched data
      this.show_book();
    }, error => {
      console.error('Error occurred while removing book:', error);
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
