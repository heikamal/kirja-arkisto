import { Component, Inject, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-valokuvat',
  standalone: true,
  imports: [],
  templateUrl: './valokuvat.component.html'
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
    @Inject(MAT_DIALOG_DATA) public data: { bookcopyid : number },
    private dataService: DataService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<ValokuvatComponent>
  ) {
    this.chosen_book = data.bookcopyid;
    }

    ngOnInit(): void {
      console.log(this.chosen_book);
      this.dataService.get_book_copy(this.chosen_book).subscribe((response) => {
        response.kuvat.forEach((kuva: any) => {
          const base64Data = kuva.picByte;
          console.log(base64Data);
          const retrievedImage = 'data:image/jpeg;base64,' + base64Data;
          console.log(retrievedImage);
          
          // Create image element
          const imgElement = document.createElement('img');
          imgElement.classList.add('object-cover', 'w-800', 'h-600');
          imgElement.src = retrievedImage;
          
          // Find the image container
          const imageContainer = document.getElementById('imageContainer');
          if (imageContainer) {
            // Append the image element to the container
            imageContainer.appendChild(imgElement);
          } else {
            console.error("Element with id 'imageContainer' not found.");
          }
        });
      });
    }
    
}
