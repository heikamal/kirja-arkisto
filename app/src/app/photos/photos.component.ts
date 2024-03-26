import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

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
  number : number = -1;

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
        response.kuvitukset.forEach((imageData: any) => {
          console.log(response.kuvitukset);
          const base64Data = imageData.kuva.picByte;
          const retrievedImage = 'data:image/jpeg;base64,' + base64Data;
    
          // Create image element
          const imgElement = document.createElement('img');
          imgElement.classList.add('object-cover', 'w-full', 'h-full');
          imgElement.src = retrievedImage;
    
          // Create paragraph elements for each property and set their text content
          const pElements: HTMLParagraphElement[] = [];
          ['julkaisuvuosi', 'taiteilija', 'tyyli', 'kuvaus', 'sivunro', 'kuvannimi'].forEach(property => {
            const p = document.createElement('p');
            p.textContent = `${property}: ${imageData.kuva[property]}`;
            pElements.push(p);
          });
    
          // Find the image container
          const imageContainer = document.getElementById('imageContainer');
          if (imageContainer) {
            // Append the image element and paragraph elements to the container
            imageContainer.appendChild(imgElement);
            pElements.forEach(p => imageContainer.appendChild(p));
          } else {
            console.error("Element with id 'imageContainer' not found.");
          }
        });
      });
    }
    
       closeDialog(): void {
      this.dialogRef.close();
    }
   
  }