import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [CommonModule],
  providers: [DataService],
  templateUrl: './book-details.component.html'
})
export class BookDetailsComponent implements OnInit {
  title: any;
  author: any;
  date: any;
  series: any;
  description: any;
  series_number: any;
  chosen_book: any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { bookId: number }, private dataService: DataService) {
    this.chosen_book = data.bookId;
  }

  ngOnInit(): void {
    this.dataService.get_book(this.chosen_book).subscribe((response: any[]) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      this.title = jsonObject.nimi;
      this.author = jsonObject.kirjailija;
      this.date = jsonObject.julkaisuVuosi;
      this.series_number = jsonObject.kirjaSarja;
      this.description = jsonObject.kuvaus;
    });
  }
}
