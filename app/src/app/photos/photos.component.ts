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
          const imgElement = document.createElement('img');
          imgElement.classList.add('object-cover', 'w-full', 'h-full');
          imgElement.src = retrievedImage;
          const imageContainer = document.getElementById('imageContainer');
          if (imageContainer) {
            imageContainer.appendChild(imgElement);
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