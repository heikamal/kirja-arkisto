import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Book } from '../book';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-carousel',
  standalone: true,
  imports: [CommonModule],
  providers: [DataService],
  templateUrl: './carousel.component.html'
})
export class CarouselComponent implements OnInit{
  books: Book[] = [];

  constructor(private dataService: DataService) { }
  
  ngOnInit(): void {
    this.dataService.get_books().subscribe((response: any[]) => { // Adjust the type to any[]
      this.books = response.map((bookData: any) => {
        return {
          id: bookData.id,
          title: bookData.nimi,
          author: bookData.kirjailija,
          date: bookData.julkaisuVuosi,
          series: bookData.jarjestysNro,
          image_url: "none"
          // Add other properties as needed
        } as Book; // Specify the type assertion to Book
      });
      console.log(this.books); // Print books array into the console
    });
  }
}
