import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DataService } from '../data.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-valokuva',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: './valokuva.component.html'
})
export class ValokuvaComponent {
  valokuva_form: FormGroup;
  selectedFile: any;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: any;
  imageName: any;
   
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { bookcopyid: number},
    private dataService: DataService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ValokuvaComponent>
  ) {
    this.valokuva_form = fb.group({
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
  bookid : string = this.data.bookcopyid.toString();

    
    onUpload() {
      if (this.valokuva_form.valid) {
        const formData = new FormData();
        formData.append("kuvannimi", this.valokuva_form.value.kuvannimi);
        formData.append("julkaisuvuosi", this.valokuva_form.value.julkaisuvuosi);
        formData.append("taiteilija", this.valokuva_form.value.taiteilija);
        formData.append("tyyli", this.valokuva_form.value.tyyli);
        formData.append("kuvaus", this.valokuva_form.value.kuvaus);
        formData.append("sivunro", this.valokuva_form.value.sivunro);
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
        
        this.dataService.post_valokuva(this.bookid, formData).subscribe((response) => {
          console.log(response);
          this.dialogRef.close();
          
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

