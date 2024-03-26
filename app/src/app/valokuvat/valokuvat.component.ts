import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-valokuvat',
  standalone: true,
  imports: [],
  templateUrl: './valokuvat.component.html',
  styleUrl: './valokuvat.component.css'
})
export class ValokuvatComponent implements OnInit {
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
    public dialogRef: MatDialogRef<ValokuvatComponent>
  ) {
    this.chosen_book = data.bookId;
    }

    ngOnInit(): void {
      this.dataService.get_book_copy(this.chosen_book).subscribe((response) => {
        response.kuvat.forEach((imageData: any) => {
          console.log(response.kuvat);
          const base64Data = imageData.kuva.picByte;
          const retrievedImage = 'data:image/jpeg;base64,' + base64Data;
    
          // Create image element
          const imgElement = document.createElement('img');
          imgElement.classList.add('object-cover', 'w-800', 'h-600');
          imgElement.src = retrievedImage;
    
          // Create paragraph elements for each property and set their text content
          const pElements: HTMLParagraphElement[] = [];
          ['julkaisuvuosi', 'taiteilija', 'tyyli', 'kuvaus', 'idkuva', 'kuvannimi'].forEach(property => {
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
}
