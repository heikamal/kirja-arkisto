import { CommonModule } from '@angular/common';
import { HttpErrorResponse, HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { CookieService } from 'ngx-cookie-service';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';


@Component({
  selector: 'app-photo',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],
  providers: [DataService],
  templateUrl: './photo.component.html',
  styleUrl: './photo.component.css'
})
export class PhotoComponent {
  
  photo_form: FormGroup;
  selectedFile: any;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: any;
  imageName: any;
   
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { bookId: number, title: string },
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<PhotoComponent>
  ) {
    this.photo_form = fb.group({
      tiedosto : [null],
      julkaisuvuosi: [''],
      taiteilija: ['', Validators.maxLength(45)],
      tyyli: ['', Validators.maxLength(45)],
      kuvaus:['', Validators.maxLength(255)],
      sivunro: [''],
      kuvannimi : ['', Validators.maxLength(45)]
    });
  }

  
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }
    bookid : string = this.data.bookId.toString();

    
    onUpload() {
      if (this.photo_form.valid) {
        const formData = new FormData();
        Object.keys(this.photo_form.value).forEach(key => {
          const value = this.photo_form.value[key];
          formData.append(key, value);
        });
        const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
        if (fileInput) {
          const files = fileInput.files;
          if (files && files.length > 0) {
            const file = files[0];
            formData.append('tiedosto', file);
          } else {
            console.error('No files selected');
            this.openErrorDialog();
            
          }
        } else {
          console.error('File input not found');
          this.openErrorDialog();
        }
        
        this.dataService.post_photo(this.bookid, formData).subscribe((response) => {
          console.log(response);
          
        })
      
    }
  }
  closeDialog(): void {
    this.dialogRef.close();
  }
  openErrorDialog(): void {
    const dialogRef = this.dialog.open(ErrorDialogComponent, {
      width: '250px',
      data: { message: 'Tarkista kentät, kuvaa ei pystytty lisäämään' }
    });
  }

}