import { Component } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-books',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './books.component.html'
})
export class BooksComponent {
  books: Book[] = [];
  displayedBooks: Book[] = [];
  remainingBooks: Book[] = [];
  retrieveResonse: any;
  base64Data: any;
  retrievedImage : any;
  url : any;
  image_url : string = "";

  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
   
    this.dataService.get_books().subscribe((response: any[]) => {
      this.books = response.map((bookData: any) => {
        let image_url = 'none';
        try {
          if (bookData.kuvitukset && bookData.kuvitukset.length > 0 && bookData.kuvitukset[0].kuva.picByte) {
            this.base64Data = bookData.kuvitukset[0].kuva.picByte;
            image_url = 'data:image/jpeg;base64,' + this.base64Data;
          }
        } catch (error) {
          console.error("Error processing image:", error);
        }
        return {
          id: bookData.id,
          title: bookData.nimi,
          author: bookData.kirjailija,
          date: bookData.julkaisuVuosi,
          series: bookData.jarjestysNro,
          image_url: image_url,
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
