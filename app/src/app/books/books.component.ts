import { Component } from '@angular/core';
import { Book } from '../book';
import { DataService } from '../data.service';
@Component({
  selector: 'app-books',
  standalone: true,
  imports: [],
  templateUrl: './books.component.html'
})
export class BooksComponent {
  books: Book[] = [];

  constructor(private dataService: DataService) { }
  
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
}
