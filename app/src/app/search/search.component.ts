import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [FormsModule, CommonModule],
  providers: [DataService],
  templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit {
  books: Book[] = [];
  filteredBooks: Book[] = [];
  searchTerm: string = '';

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.dataService.get_books().subscribe((response: any[]) => {
      this.books = response.map((bookData: any) => {
        return {
          id: bookData.id,
          title: bookData.nimi,
          author: bookData.kirjailija,
          date: bookData.julkaisuVuosi,
          series: bookData.jarjestysNro,
          image_url: bookData.imageUrl
        } as Book;
      });
      this.filteredBooks = this.books;
    });
  }

  filterBooks(): void {
    if (!this.searchTerm) {
      this.filteredBooks = this.books;
      return;
    }
    this.filteredBooks = this.books.filter(book =>
      book.title.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  showBookDetails(bookId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { bookId };
    const dialogRef = this.dialog.open(BookDetailsComponent, dialogConfig);
  }
}
