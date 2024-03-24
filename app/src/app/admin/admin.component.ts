import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { DataService } from '../data.service';
import { Series } from '../series';
import { CommonModule } from '@angular/common';
import { Book } from '../book';

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
  registration_form: FormGroup;
  roles: string[] = ["user"]
  book_data: any;
  single_book_data: any;
  series_data: any;
  series_list: Series[] = [];
  book_list: Book[] = [];

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
    this.registration_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      salasana: ['', Validators.maxLength(45)],
      rooli: [this.roles]
    });

    this.load_series();
    this.load_books();
    console.log(this.book_list)
  }

  load_series() {
    this.dataService.get_series().subscribe(response => {
      this.series_list = response;
    });
  }
  load_books() {
    this.dataService.get_books().subscribe((response: any[]) => {
      this.book_list = response.map((bookData: any) => {
        return {
          id: bookData.id,
          title: bookData.nimi,
        } as Book;
      });
    });
  }
  submit_book() {
    if (this.book_form.valid) {
      this.dataService.post_book(this.book_form.value).subscribe(response => {
        console.log('Response:', response);
        this.load_books();
      })
    } (error: HttpErrorResponse) => {
      console.error('Error:', error);
    }
  }
  remove_book() {
    this.dataService.remove_book(this.remove_book_id).subscribe(() => {
      this.load_books();
    }, error => {
      console.error('Error occurred while removing book:', error);
    });
  }
  submit_series() {
    if (this.series_form.valid) {
      this.dataService.post_series(this.series_form.value).subscribe(response => {
        console.log('Response:', response);
        this.load_series();
      })
    } (error: HttpErrorResponse) => {
      console.error('Error:', error);
    }
  }
  remove_series() {
    this.dataService.remove_series(this.remove_series_id).subscribe(() => {
      this.load_series();
    }, error => {
      console.error('Error occurred while removing book:', error);
    });
  }
  register_user() {
    this.dataService.register_user(this.registration_form
  .value).subscribe(response => {
      console.log('Response:', response);
    })
  }
}
