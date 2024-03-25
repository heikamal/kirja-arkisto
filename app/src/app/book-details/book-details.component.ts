import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { AddCopyComponent } from '../add-copy/add-copy.component';
import { PhotoComponent } from '../photo/photo.component';

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
  kuvitukset: any;
  retrieveResonse: any;
  base64Data: any;
  retrievedImage : any;
  show_full_description: boolean = false;

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
      try {
        const jsonStr: string = JSON.stringify(response);
        const jsonObject: any = JSON.parse(jsonStr);
        this.title = jsonObject.nimi;
        this.author = jsonObject.kirjailija;
        this.date = jsonObject.julkaisuVuosi;
        this.series_name = jsonObject.kirjaSarja.title;
        this.series_id = jsonObject.kirjaSarja.id;
        this.description = jsonObject.kuvaus;
        this.retrieveResonse = response;
  
        if (this.retrieveResonse.kuvitukset && this.retrieveResonse.kuvitukset.length > 0 && this.retrieveResonse.kuvitukset[0].kuva.picByte) {
          this.base64Data = this.retrieveResonse.kuvitukset[0].kuva.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
          this.image_url = this.retrievedImage;
        } else {
          this.image_url = 'none';
        }

        console.log(this.image_url);
      } catch (error) {
        console.error("Error processing book data:", error);
      }
    });
  
    this.dataService.get_ownership(this.chosen_book).subscribe((response: any) => {
      try {
        const jsonStr: string = JSON.stringify(response);
        const jsonObject: any = JSON.parse(jsonStr);
        this.owned = jsonObject.owned;
      } catch (error) {
        console.error("Error processing ownership data:", error);
      }
    });
  }
  

  toggle_description(): void {
    this.show_full_description = !this.show_full_description;
  }
  
  add_copy(): void {
    const dialogRef = this.dialog.open(AddCopyComponent, {
      width: '400px',
      data: { bookId: this.chosen_book, title: this.title, series_id: this.series_id}
    });
  }
  add_photo(): void {
    if (this.dialogRef && this.dialogRef.componentInstance instanceof AddCopyComponent) {
      this.dialogRef.close();
    }
    const dialogRef = this.dialog.open(PhotoComponent, {
      width: '400px',
      data: { bookId: this.chosen_book, title: this.title }
    });
  }
  closeDialog(): void {
    this.dialogRef.close();
  }
 
}
