import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { BookCopy } from '../book-copy';
import { CommonModule } from '@angular/common';
import { ValokuvaComponent } from '../valokuva/valokuva.component';

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
  copy_form: FormGroup; // Define a FormGroup for the edited copy

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { copyId: number },
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<BookCopyDetailsComponent>
  ) {
    // Initialize the copy_form FormGroup
    this.copy_form = this.fb.group({
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
        this.copy_form.patchValue({
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
        console.error('Error:', error);
      }
    );
  }

  toggleEdit(): void {
    this.editing = !this.editing;
    // Initialize editedCopy with current values when entering edit mode
    if (this.editing) {
      this.editedCopy = { ...this.copy } as BookCopy;
    }
  }

  saveChanges(): void {
    // Send editedCopy to the API
    console.log('Edited data:', this.editedCopy);
    
    // Manually update the form values with the edited data
    this.copy_form.patchValue({
      edition: this.editedCopy.edition,
      editionYear: this.editedCopy.editionYear,
      purchasePrice: this.editedCopy.purchasePrice,
      purchaseDate: this.editedCopy.purchaseDate,
      condition: this.editedCopy.condition,
      description: this.editedCopy.description,
      saleDate: this.editedCopy.saleDate,
      salePrice: this.editedCopy.salePrice,
    });
    
    // Extract the updated form values
    const editedData = this.copy_form.value;
    
    // Call the edit_book_copy method to update the data
    this.dataService.edit_book_copy(this.data.copyId, editedData).subscribe(
      (response: any) => {
        console.log('Data edited successfully:', response);
        // Update the copy object with edited data
        this.copy = { ...this.editedCopy };
        // Exit editing mode
        this.editing = false;
        // Close the dialog or perform any other action as needed
        this.closeDialog();
      },
      (error: HttpErrorResponse) => {
        console.error('Error editing data:', error);
        // Handle error scenario
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

  closeDialog(): void {
    this.dialogRef.close();
  }
}
