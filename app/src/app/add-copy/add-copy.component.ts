import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DataService } from '../data.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-copy',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  templateUrl: './add-copy.component.html'
})
export class AddCopyComponent {
  copy_form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { bookId: number, title: string, series_id: number },
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
  ) {
    this.copy_form = this.fb.group({
      nimi: [this.data.title],
      painos: ['', Validators.required],
      painosVuosi: ['', Validators.max(2030)],
      kirjaId: [this.data.bookId],
      ostoHinta: ['', Validators.required],
      ostoPvm: ['', Validators.required],
      kunto: ['', Validators.required],
      kuvaus: ['', Validators.maxLength(255)],
      myyntiPvm: [''],
      myyntiHinta: [''],
      idKirjaSarja: [this.data.series_id]
    });
  }

  on_submit() {
    if (this.copy_form.valid) {
      this.dataService.post_book_copy(this.copy_form.value).subscribe(response => {
        console.log('Response:', response);
      }, (error: HttpErrorResponse) => {
        console.error('Error:', error);
      });
    }
  }
}
