import { Component, Inject, OnInit } from '@angular/core';
import { Init } from 'v8';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-series-details',
  standalone: true,
  imports: [CommonModule],
  providers: [DataService],
  templateUrl: './series-details.component.html'
})
export class SeriesDetailsComponent implements OnInit {
  chosen_series: any;
  title: any;
  publisher: any;
  description: any;
  category: any;


  constructor(@Inject(MAT_DIALOG_DATA) public data: { seriesId: number }, private dataService: DataService, public dialogRef: MatDialogRef<SeriesDetailsComponent>) {
    this.chosen_series = data.seriesId;
  }


  ngOnInit(): void {
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
}
