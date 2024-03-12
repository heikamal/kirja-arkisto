import { Component, Inject, OnInit } from '@angular/core';
import { Init } from 'v8';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DataService } from '../data.service';

@Component({
  selector: 'app-series-details',
  standalone: true,
  imports: [],
  providers: [DataService],
  templateUrl: './series-details.component.html',
  styleUrl: './series-details.component.css'
})
export class SeriesDetailsComponent implements OnInit {
  chosen_series: any;
  title: any;
  kustantaja: any;
  kuvaus: any;
  luokittelu: any;


  constructor(@Inject(MAT_DIALOG_DATA) public data: { seriesid: number }, private dataService: DataService) {
    this.chosen_series = data.seriesid;
  }

  ngOnInit(): void {
    this.dataService.get_series_info(this.chosen_series).subscribe((response: any[]) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
      this.title = jsonObject.title;
      this.kustantaja = jsonObject.kustantaja;
      this.kuvaus = jsonObject.kuvaus;
      this.luokittelu = jsonObject.luokittelu;
    });


  }
}
