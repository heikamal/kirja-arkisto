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
  base64Data : any;
  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.get_books().subscribe((response: any[]) => {
      
      if (this.seriesId) {
        this.books = response.filter(book => book.kirjaSarja.id === this.seriesId)
          .map((bookData: any) => {
            let image_url = 'none';
      
            try {
              if (bookData.kuvitukset && bookData.kuvitukset.length > 0 && bookData.kuvitukset[0].kuva.picByte) {
                this.base64Data = bookData.kuvitukset[0].kuva.picByte;
                image_url = 'data:image/jpeg;base64,' + this.base64Data;
              }
            } catch (error) {
              console.error("Error processing image:", error);
            }
      
            const book: Book = {
              id: bookData.id,
              title: bookData.nimi,
              author: bookData.kirjailija,
              date: bookData.julkaisuVuosi,
              series: bookData.jarjestysNro,
              image_url: image_url,
              is_owned: false
            };
      
            this.dataService.get_ownership(book.id).subscribe((ownershipResponse: any) => {
              try {
                const jsonStr: string = JSON.stringify(ownershipResponse);
                const jsonObject: any = JSON.parse(jsonStr);
                book.is_owned = jsonObject.owned;
              } catch (error) {
                console.error("Error parsing ownership data:", error);
              }
            });
            return book;
          });
      }
       else {
        this.books = response.map((bookData: any) => {
          let image_url = 'none';
          try {
            if (bookData.kuvitukset && bookData.kuvitukset.length > 0 && bookData.kuvitukset[0].kuva.picByte) {
              this.base64Data = bookData.kuvitukset[0].kuva.picByte;
              image_url = 'data:image/jpeg;base64,' + this.base64Data;
            }
          } catch (error) {
            console.error("Error processing image:", error);
          }
          return {
            id: bookData.id,
            title: bookData.nimi,
            author: bookData.kirjailija,
            date: bookData.julkaisuVuosi,
            series: bookData.jarjestysNro,
            image_url: image_url,
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
