import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { BookCopy } from '../book-copy';
import { CommonModule } from '@angular/common';
import { ValokuvaComponent } from '../valokuva/valokuva.component';
import { ValokuvatComponent } from '../valokuvat/valokuvat.component';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  selector: 'app-book-copy-details',
  templateUrl: './book-copy-details.component.html'
})
export class BookCopyDetailsComponent implements OnInit {
  copy: BookCopy | undefined;
  editing: boolean = false;
  editedCopy: BookCopy = {} as BookCopy;
  copyForm: FormGroup; // Define a FormGroup for the edited copy

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { copyId: number },
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<BookCopyDetailsComponent>
  ) {
    // Initialize the copyForm FormGroup
    this.copyForm = this.fb.group({
      edition: ['', Validators.required],
      editionYear: ['', Validators.max(2030)],
      purchasePrice: ['', Validators.required],
      purchaseDate: ['', Validators.required],
      condition: ['', Validators.required],
      description: ['', Validators.maxLength(255)],
      saleDate: [''],
      salePrice: [''],
    });
  }

  ngOnInit(): void {
    this.reloadData();
  }

  toggleEdit(): void {
    this.editing = !this.editing;
    if (this.editing && this.copy) {
      // If entering edit mode and copy is defined, initialize the form with the current copy data
      this.copyForm.setValue({
        edition: this.copy.edition,
        editionYear: this.copy.editionYear,
        purchasePrice: this.copy.purchasePrice,
        purchaseDate: this.copy.purchaseDate,
        condition: this.copy.condition,
        description: this.copy.description,
        saleDate: this.copy.saleDate,
        salePrice: this.copy.salePrice,
      });
    } else {
      // If canceling edit mode, reset the form to display original copy data
      this.copyForm.reset();
    }
  }

  saveChanges(): void {
    // Manually update the editedCopy with the form values
    this.editedCopy.edition = this.copyForm.value.edition;
    this.editedCopy.editionYear = this.copyForm.value.editionYear;
    this.editedCopy.purchasePrice = this.copyForm.value.purchasePrice;
    this.editedCopy.purchaseDate = this.copyForm.value.purchaseDate;
    this.editedCopy.condition = this.copyForm.value.condition;
    this.editedCopy.description = this.copyForm.value.description;
    this.editedCopy.saleDate = this.copyForm.value.saleDate;
    this.editedCopy.salePrice = this.copyForm.value.salePrice;

    // Construct the data object to be sent to the server
    const editedData = {
      nimi: this.editedCopy.name,
      painos: this.editedCopy.edition,
      painosVuosi: this.editedCopy.editionYear,
      kirjaId: this.editedCopy.book.id,
      ostoHinta: this.editedCopy.purchasePrice,
      ostoPvm: this.editedCopy.purchaseDate,
      kunto: this.editedCopy.condition,
      kuvaus: this.editedCopy.description,
      myyntiPvm: this.editedCopy.saleDate || '', // Ensure it's an empty string if saleDate is null
      myyntiHinta: this.editedCopy.salePrice,
      idKirjaSarja: this.editedCopy.seriesId
    };

    // Call the edit_book_copy method to update the data
    this.dataService.edit_book_copy(this.data.copyId, editedData).subscribe(
      (response: any) => {
        console.log('Data edited successfully:', response);
        // Update the copy object with edited data
        this.copy = { ...this.editedCopy };
        // Exit editing mode
        this.editing = false;
        // Reload the data to display the updated information
        this.reloadData();
        // Close the dialog or perform any other action as needed
        // this.closeDialog();
      },
      (error: HttpErrorResponse) => {
        console.error('Error editing data:', error);
        // Handle error scenario
      }
    );
  }

  removeCopyFromBookshelf(): void {
    this.dataService.remove_book_copy(this.data.copyId).subscribe(
      (response: any) => {
        this.reloadData();
        this.closeDialog();
      },
      (error: HttpErrorResponse) => {
        console.error('Error removing copy from bookshelf:', error);
      }
    );
  }

  reloadData(): void {
    this.dataService.get_book_copy(this.data.copyId).subscribe(
      (response: any) => {
        this.copy = {
          id: response.id,
          name: response.title,
          edition: response.editions,
          editionYear: response.editionYear,
          book: {
            id: response.book.id,
            title: response.book.nimi,
            author: response.book.kirjailija,
            date: response.book.julkaisuVuosi,
            series: response.book.kirjaSarja.jarjestysNro,
            image_url: response.book.image_url,
            is_owned: true
          },
          purchasePrice: response.purchasePrice,
          purchaseDate: response.purchaseDate,
          condition: response.condition,
          description: response.description,
          saleDate: response.saleDate,
          salePrice: response.salePrice,
          bookshelfId: response.idKirjaHylly,
          seriesId: response.idKirjaSarja
        };
        // Initialize editedCopy with values from copy
        this.editedCopy = { ...this.copy };
        // Patch the values to the form
        this.copyForm.patchValue({
          edition: this.copy.edition,
          editionYear: this.copy.editionYear,
          purchasePrice: this.copy.purchasePrice,
          purchaseDate: this.copy.purchaseDate,
          condition: this.copy.condition,
          description: this.copy.description,
          saleDate: this.copy.saleDate,
          salePrice: this.copy.salePrice,
        });
      },
      (error: HttpErrorResponse) => {
        console.error('Error reloading data:', error);
      }
    );
  }

  
  add_photos(): void {
    if (this.dialogRef && this.dialogRef.componentInstance instanceof BookCopyDetailsComponent) {
      this.dialogRef.close();
    }
    const dialogRef = this.dialog.open(ValokuvaComponent, {
      width: '400px',
      data: { bookcopyid: this.data.copyId}
    });
  }
  show_photos(): void {
    if (this.dialogRef && this.dialogRef.componentInstance instanceof BookCopyDetailsComponent) {
      this.dialogRef.close();
    }
    const dialogRef = this.dialog.open(ValokuvatComponent, {
      width: '400px',
      data: { bookcopyid: this.data.copyId}
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
