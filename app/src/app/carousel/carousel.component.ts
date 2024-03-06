import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import {MatDialogModule} from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css'] // Add any necessary styles
})
export class CarouselComponent implements OnInit {
  books: Book[] = [];

  constructor(private dataService: DataService, private dialog: MatDialogModule) { }

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
    });
  }

  openBookDetails(book: Book): void {
    const dialogRef = this.dialog.open(BookDetailsComponent, {
      width: '500px',
      data: book
    });
  }
}
