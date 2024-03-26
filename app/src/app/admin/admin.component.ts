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
  book_edit_form: FormGroup;
  registration_form: FormGroup;
  roles: string[] = ["user"]
  book_data: any;
  single_book_data: any;
  single_series_data: any;
  series_data: any;
  series_edit_form: FormGroup;
  selectedSeriesId: number | null = null;
  series_list: Series[] = [];
  book_list: Book[] = [];

  constructor(@Inject(FormBuilder) fb: FormBuilder,
    private dataService: DataService
  ) {
    this.book_edit_form = fb.group({
      nimi: ['', Validators.maxLength(45)],
      kirjailija: ['', Validators.maxLength(45)],
      julkaisuVuosi: ['', Validators.max(2030)],
      sarja: [''],
      kuvaus: ['', Validators.maxLength(255)],
      jarjestysNro: ['']
    });
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
    this.series_edit_form = fb.group({
      title: ['', Validators.maxLength(45)],
      kustantaja: ['', Validators.maxLength(45)],
      kuvaus: ['', Validators.maxLength(255)],
      luokittelu: ['', Validators.maxLength(45)],
    });

    this.load_series();
    this.load_books();
  }
  loadEditBookData(bookId: number) {
    const selectedBook = this.book_list.find(book => book.id === bookId);
    if (selectedBook) {
      this.book_edit_form.patchValue(selectedBook);
    }
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
  edit_book() {
    if (this.book_edit_form.valid && this.chosen_book_id) {
      const formData = this.book_edit_form.value;
      formData['sarja'] = parseInt(formData['sarja']); // Ensure sarja is converted to a number
      this.dataService.edit_book(this.chosen_book_id, formData).subscribe(response => {
        console.log('Book updated successfully:', response);
        this.load_books();
      }, (error: HttpErrorResponse) => {
        console.error('Error occurred while editing book:', error);
      });
    } else {
      console.error('Invalid form data or book ID is missing.');
    }
  }
  // Method to submit edited series data
  edit_series() {
    if (this.series_edit_form.valid && this.selectedSeriesId) {
      // Extract edited series data from form
      const editedSeriesData = this.series_edit_form.value;
      // Send PUT request to update series data
      this.dataService.edit_series(this.selectedSeriesId, editedSeriesData).subscribe(
        response => {
          console.log('Series updated successfully:', response);
          // Reload series data after editing
          this.load_series();
        },
        error => {
          console.error('Error occurred while editing series:', error);
        }
      );
    } else {
      console.error('Invalid form data or series ID is missing.');
    }
  }
  loadSingleBookData() {
    // Fetch single book data based on chosen_book_id
    if (this.chosen_book_id) {
      this.dataService.get_book(this.chosen_book_id).subscribe((response: any) => {
        this.single_book_data = response;
        // Prefill form with book data
        this.book_edit_form.patchValue({
          nimi: response.nimi,
          kirjailija: response.kirjailija,
          julkaisuVuosi: response.julkaisuVuosi,
          sarja: response.kirjaSarja.id,
          kuvaus: response.kuvaus,
          jarjestysNro: response.jarjestysNro
        });
        console.log(this.book_edit_form)
      });
    }
  }
  loadSingleSeriesData() {
    // Fetch single series data based on selectedSeriesId
    if (this.selectedSeriesId !== null) {
      this.dataService.get_series_info(this.selectedSeriesId).subscribe((response: any) => {
        this.single_series_data = response;
        // Prefill form with series data
        this.series_edit_form.patchValue({
          title: response.title,
          kustantaja: response.kustantaja,
          kuvaus: response.kuvaus,
          luokittelu: response.luokittelu
        });
        console.log(this.series_edit_form)
      });
    }
  }
  
}
