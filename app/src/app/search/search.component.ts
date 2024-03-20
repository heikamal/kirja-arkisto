import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { Series } from '../series';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookDetailsComponent } from '../book-details/book-details.component';
import { SeriesDetailsComponent } from '../series-details/series-details.component';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [FormsModule, CommonModule],
  providers: [DataService],
  templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit {
  books: Book[] = [];
  series: Series[] = [];
  filteredBooks: Book[] = [];
  filteredSeries: Series[] = [];
  searchTerm: string = '';
  searchType: 'books' | 'series' = 'books';

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

    this.dataService.get_series().subscribe((response: any[]) => {
      this.series = response.map((seriesData: any) => {
        return {
          id: seriesData.id,
          title: seriesData.title,
          publisher: seriesData.kustantaja,
          category: seriesData.luokittelu
        } as Series;
      });
      this.filteredSeries = this.series;
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

  filterSeries(): void {
    if (!this.searchTerm) {
      this.filteredSeries = this.series;
      return;
    }

    this.filteredSeries = this.series.filter(series =>
      series.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      series.publisher.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      series.category.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  toggleSearchType(): void {
    this.searchType = this.searchType === 'books' ? 'series' : 'books';
    if (this.searchType === 'books') {
      this.filteredBooks = this.books;
    } else {
      this.filteredSeries = this.series;
    }
  }

  showBookDetails(bookId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { bookId };
    const dialogRef = this.dialog.open(BookDetailsComponent, dialogConfig);
  }

  showSeriesDetails(seriesId: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { seriesId };
    const dialogRef = this.dialog.open(SeriesDetailsComponent, dialogConfig);
  }
}
