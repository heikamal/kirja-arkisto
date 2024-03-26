import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { Series } from '../series';
import { CarouselComponent } from '../carousel/carousel.component';

@Component({
  selector: 'app-series-details',
  standalone: true,
  imports: [CommonModule, CarouselComponent],
  templateUrl: './series-details.component.html'
})
export class SeriesDetailsComponent implements OnInit {
  chosen_series: any;
  title: any;
  publisher: any;
  description: any;
  category: any;
  is_owned: boolean = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { seriesId: number }, private dataService: DataService, public dialogRef: MatDialogRef<SeriesDetailsComponent>) {
    this.chosen_series = data.seriesId;
  }

  ngOnInit(): void {
    this.loadSeriesDetails();
  }

  loadSeriesDetails(): void {
    this.dataService.get_bookshelf().subscribe(response => {
      const bookshelf_data: any = response;
      this.is_owned = bookshelf_data.sarjat.some((series: Series) => series.id === this.chosen_series);
    });

    this.dataService.get_series_info(this.chosen_series).subscribe((response: any[]) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      this.title = jsonObject.title;
      this.publisher = jsonObject.kustantaja;
      this.description = jsonObject.kuvaus;
      this.category = jsonObject.luokittelu;
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  add_series_to_bookshelf(): void {
    this.dataService.post_series_to_bookshelf(this.chosen_series).subscribe(response => {
      console.log('Response:', response);
      // After adding, reload series details to update ownership status
      this.loadSeriesDetails();
    });
  }

  remove_series_from_bookshelf(): void {
    this.dataService.remove_series_from_bookshelf(this.chosen_series).subscribe(response => {
      console.log('Response:', response);
      // After removing, reload series details to update ownership status
      this.loadSeriesDetails();
    });
  }
}
