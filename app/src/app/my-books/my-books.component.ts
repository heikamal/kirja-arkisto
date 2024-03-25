import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { BookCopy } from '../book-copy';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { BookCopyDetailsComponent } from '../book-copy-details/book-copy-details.component';

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-books.component.html'
})
export class MyBooksComponent implements OnInit {
  copies: BookCopy[] = [];
  displayedCopies: BookCopy[] = [];
  remainingCopies: BookCopy[] = [];

  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.get_bookshelf().subscribe((response: any) => {
      this.copies = [];
      response.kopiot.forEach((copy: any) => {
        let image_url = 'none';

        try {
          if (copy.kirja.kuvitukset && copy.kirja.kuvitukset.length > 0 && copy.kirja.kuvitukset[0].kuva.picByte) {
            const base64Data = copy.kirja.kuvitukset[0].kuva.picByte;
            image_url = 'data:image/jpeg;base64,' + base64Data;
          }
        } catch (error) {
          console.error("Error processing image:", error);
        }
        
        console.log(image_url); // Logging the image URL for debugging
        
        this.copies.push({
          id: copy.id,
          name: copy.nimi,
          edition: copy.painos,
          editionYear: copy.painosVuosi,
          book: {
            id: copy.kirja.id,
            title: copy.kirja.nimi,
            author: copy.kirja.kirjailija,
            date: copy.kirja.julkaisuVuosi,
            series: copy.kirja.jarjestysNro,
            image_url: image_url, // Assigning the image URL here
            is_owned: true
          },
          purchasePrice: copy.ostoHinta,
          purchaseDate: copy.ostoPvm,
          condition: copy.kunto,
          description: copy.kuvaus,
          saleDate: copy.myyntiPvm,
          salePrice: copy.myyntiHinta,
          bookshelfId: copy.idKirjaHylly,
          seriesId: copy.idKirjaSarja
        });
      });
      this.displayedCopies = this.copies.slice(0, 8);
      this.remainingCopies = this.copies.slice(8);
    });
  }

  loadMoreCopies(): void {
    const nextCopies = this.remainingCopies.splice(0, 8);
    this.displayedCopies = this.displayedCopies.concat(nextCopies);
  }

  show_copy_details(copyId: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { copyId };
    const dialogRef = this.dialog.open(BookCopyDetailsComponent, dialogConfig);
  }
}
