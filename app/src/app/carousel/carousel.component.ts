import { Component, OnInit } from '@angular/core';
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
  books: Book[] = [];

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
          image_url: "none"
        } as Book;
      });
      console.log(this.books);
    });
  }

  showBookId(bookId: number) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.width = '4/5';
    dialogConfig.data = { bookId };

    const dialogRef = this.dialog.open(BookDetailsComponent, dialogConfig);
  }
}
