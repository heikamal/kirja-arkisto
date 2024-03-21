import { CommonModule } from '@angular/common';
import { HttpErrorResponse, HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { CookieService } from 'ngx-cookie-service';


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

  constructor(private dataService: DataService, fb: FormBuilder, private httpClient: HttpClient, private cookieService: CookieService) {
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
    bookid : string = "1";

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
          }
        } else {
          console.error('File input not found');
        }
        
        const accessToken = this.cookieService.get('accessToken');
        const headers = new HttpHeaders({
          'Authorization': `Bearer ${accessToken}`,
        });
  
        this.httpClient.post("http://localhost:8080/api/kirjat/" + this.bookid + "/kuvat", formData, { headers, observe: 'response' })
          .subscribe(
            (response) => {
              console.log(response);
              if (response.status === 200) {
                this.message = 'Image uploaded successfully';
              } else {
                this.message = 'Image not uploaded successfully';
              }
            },
            (error: HttpErrorResponse) => {
              console.error('Error:', error);
              this.message = 'Error uploading image';
            }
          );
      } else {
        console.error('Form is invalid');
      }
    }
  }
  