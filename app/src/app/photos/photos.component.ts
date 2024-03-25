import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-photos',
  standalone: true,
  imports: [],
  templateUrl: './photos.component.html',
  styleUrl: './photos.component.css'
})
export class PhotosComponent implements OnInit{
  response : any;
  chosen_book: any;
  base64Data: any;
  retrievedImage : any;
  image_url: any;
  i : any;
  number : number = 0;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { bookId: number },
    private dataService: DataService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<PhotosComponent>
  ) {
    this.chosen_book = data.bookId;
    }
    ngOnInit(): void {
      this.dataService.get_book(this.chosen_book).subscribe((response) => {
        for (this.i in response.kuvitukset) {
        this.base64Data = response.kuvitukset[this.number].kuva.picByte;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        const imgElement = document.createElement('img');
        imgElement.src = this.retrievedImage;
        // Append image element to some container in your HTML, such as a div with id "imageContainer"
        const imageContainer = document.getElementById('imageContainer');
        if (imageContainer) {
          imageContainer.appendChild(imgElement);
        } else {
          console.error("Element with id 'imageContainer' not found.");
        }
        this.number = this.number+1;
      };
    
    });

  }
}
 
