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
        this.dataService.get_book(bookData.id).subscribe((bookresponse: any) => {
          const base64Data = bookresponse.kuvitukset[0].kuva.picByte;
          this.url = 'data:image/jpeg;base64,' + base64Data;
          bookData.image_url = this.url;
          console.log(bookData.image_url);
        });
        return {
          id: bookData.id,
          title: bookData.nimi,
          author: bookData.kirjailija,
          date: bookData.julkaisuVuosi,
          series: bookData.jarjestysNro,
          image_url: bookData.image_url,
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
