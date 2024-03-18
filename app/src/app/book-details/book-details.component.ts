// book-details.component.ts
import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { response } from 'express';
import { AddCopyComponent } from '../add-copy/add-copy.component';

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [CommonModule, AddCopyComponent],
  providers: [DataService],
  templateUrl: './book-details.component.html'
})
export class BookDetailsComponent implements OnInit {
  title: any;
  author: any;
  date: any;
  series: any;
  description: any;
  series_name: any;
  series_id: any;
  chosen_book: any;
  image_url: any;
  owned: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { bookId: number},
    private dataService: DataService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<BookDetailsComponent>
  ) {
    this.chosen_book = data.bookId;
  }

  ngOnInit(): void {
    this.dataService.get_book(this.chosen_book).subscribe((response: any[]) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      this.title = jsonObject.nimi;
      this.author = jsonObject.kirjailija;
      this.date = jsonObject.julkaisuVuosi;
      this.series_name = jsonObject.kirjaSarja.title;
      this.series_id = jsonObject.kirjaSarja.id;
      this.description = jsonObject.kuvaus;
      this.image_url = jsonObject.image_url;
    });
    this.dataService.get_ownership(this.chosen_book).subscribe((response: any) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      this.owned = jsonObject.owned;
    });
  }
  
  add_copy(): void {
    const dialogRef = this.dialog.open(AddCopyComponent, {
      width: '400px',
      data: { bookId: this.chosen_book, book_title: this.title, series_id: this.series_id}
    });
  }
  closeDialog(): void {
    this.dialogRef.close();
  }
}
