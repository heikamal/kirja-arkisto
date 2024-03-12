import { Component } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-books.component.html'
})
export class MyBooksComponent {
  books: Book[] = [];
  displayedBooks: Book[] = [];
  remainingBooks: Book[] = [];

  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.get_books().subscribe((response: any[]) => {
      this.books = response.map((bookData: any) => {
        return {
          id: bookData.id,
          title: bookData.nimi,
          author: bookData.kirjailija,
          date: bookData.julkaisuVuosi,
          series: bookData.jarjestysNro,
          image_url: bookData.image_url
        } as Book;
      });
      this.displayedBooks = this.books.slice(0, 8);
      this.remainingBooks = this.books.slice(8);
    });
  }

  loadMoreBooks(): void {
    const nextBooks = this.remainingBooks.splice(0, 8);
    this.displayedBooks = this.displayedBooks.concat(nextBooks);
  }

  show_book_details(bookId: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { bookId };
    const dialogRef = this.dialog.open(BookDetailsComponent, dialogConfig);
  }
}
