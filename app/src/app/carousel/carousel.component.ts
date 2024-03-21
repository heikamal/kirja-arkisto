import { Component, Input, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-carousel',
  standalone: true,
  imports: [CommonModule],
  providers: [DataService],
  templateUrl: './carousel.component.html'
})
export class CarouselComponent implements OnInit {
  @Input() seriesId?: number;
  books: Book[] = [];

  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.get_books().subscribe((response: any[]) => {
      if (this.seriesId) {
        this.books = response.filter(book => book.kirjaSarja.id === this.seriesId)
          .map((bookData: any) => {
            const book: Book = {
              id: bookData.id,
              title: bookData.nimi,
              author: bookData.kirjailija,
              date: bookData.julkaisuVuosi,
              series: bookData.jarjestysNro,
              image_url: bookData.image_url,
              is_owned: false
            };
            this.dataService.get_ownership(book.id).subscribe((ownershipResponse: any) => {
              const jsonStr: string = JSON.stringify(ownershipResponse);
              const jsonObject: any = JSON.parse(jsonStr);
              book.is_owned = jsonObject.owned;
            });
            return book;
          });
      } else {
        this.books = response.map((bookData: any) => {
          return {
            id: bookData.id,
            title: bookData.nimi,
            author: bookData.kirjailija,
            date: bookData.julkaisuVuosi,
            series: bookData.jarjestysNro,
            image_url: bookData.image_url,
            is_owned: false
          } as Book;
        });
      }
    });
  }

  show_book_details(bookId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { bookId };
    const dialogRef = this.dialog.open(BookDetailsComponent, dialogConfig);
  }
}
