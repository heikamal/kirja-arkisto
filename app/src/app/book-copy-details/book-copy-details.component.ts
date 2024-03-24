import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { BookCopy } from '../book-copy';

@Component({
  standalone: true,
  selector: 'app-book-copy-details',
  templateUrl: './book-copy-details.component.html'
})
export class BookCopyDetailsComponent implements OnInit {
  copy: BookCopy | undefined;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { copyId: number },
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<BookCopyDetailsComponent>
  ) {}

  ngOnInit(): void {
    this.dataService.get_book_copy(this.data.copyId).subscribe(
      (response: any) => {
        this.copy = {
          id: response.id,
          name: response.title, // Changed from response.nimi
          edition: response.editions, // Changed from response.painos
          editionYear: response.editionYear, // Changed from response.painosVuosi
          book: {
            id: response.book.id,
            title: response.book.nimi, // Changed from response.book.title
            author: response.book.kirjailija,
            date: response.book.julkaisuVuosi,
            series: response.book.kirjaSarja.jarjestysNro,
            image_url: response.book.image_url, // Assuming the image_url property exists
            is_owned: true // Assuming this property is always true for owned books
          },
          purchasePrice: response.purchasePrice, // No change needed
          purchaseDate: response.purchaseDate, // No change needed
          condition: response.condition, // No change needed
          description: response.description, // No change needed
          saleDate: response.saleDate, // No change needed
          salePrice: response.salePrice, // No change needed
          bookshelfId: response.idKirjaHylly, // No change needed
          seriesId: response.idKirjaSarja // No change needed
        };
      },
      (error: HttpErrorResponse) => {
        console.error('Error:', error);
      }
    );
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}