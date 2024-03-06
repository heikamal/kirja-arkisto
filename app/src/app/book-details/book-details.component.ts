import { Component } from '@angular/core';

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [],
  templateUrl: './book-details.component.html'
})
export class BookDetailsComponent {
  book_data: any;
  chosen_book_id: any; 
}
