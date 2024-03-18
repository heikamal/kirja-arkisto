import { Component } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { DataService } from '../data.service';

@Component({
  selector: 'app-photo',
  standalone: true,
  imports: [DataService],
  templateUrl: './photo.component.html',
  styleUrl: './photo.component.css'
})
export class PhotoComponent {
  constructor(private dataService: DataService, private httpClient: HttpClient) { }

  selectedFile: File = new File([], 'default.jpg');
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string = "";
  imageName: any;
  public onFileChanged(event: { target: { files: File[]; }; }) {
        //Select File
  this.selectedFile = event.target.files[0];
  }
      //Gets called when the user clicks on submit to upload the image
      onUpload() {
        console.log(this.selectedFile);
        //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
        const uploadImageData = new FormData();
        uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
        //Make a call to the Spring Boot Application to save the image
        this.httpClient.post('http://localhost:8080/api/kirjat/kuvat', uploadImageData, { observe: 'response' })
          .subscribe((response) => {
            if (response.status === 200) {
              this.message = 'Image uploaded successfully';
            } else {
              this.message = 'Image not uploaded successfully';
            }
          }
          );
      }
        //Gets called when the user clicks on retieve image button to get the image from back end
        getImage() {
        //Make a call to Sprinf Boot to get the Image Bytes.
        this.httpClient.get('http://localhost:8080/api/kirjat/kuvat' + this.imageName)
          .subscribe(
            res => {
              this.retrieveResonse = res;
              this.base64Data = this.retrieveResonse.picByte;
              this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
            }
    
          );
    
      }
}
