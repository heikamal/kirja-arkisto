import { Component } from '@angular/core';
import { DataService } from '../data.service';
import { Series } from '../series';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { SeriesDetailsComponent } from '../series-details/series-details.component';

@Component({
  selector: 'app-my-series',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-series.component.html'
})
export class MySeriesComponent {
  series: Series[] = [];
  displayedSeries: Series[] = [];
  remainingSeries: Series[] = [];

  constructor(private dataService: DataService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.dataService.get_bookshelf().subscribe((response: any) => {
      this.series = [];
      response.sarjat.forEach((s: any) => {
        const series: Series = {
          id: s.id,
          title: s.title,
          publisher: s.kustantaja,
          description: s.kuvaus,
          category: s.luokittelu
        };
        this.series.push(series);
      });
      this.displayedSeries = this.series.slice(0, 8);
      this.remainingSeries = this.series.slice(8);
    });
  }

  load_more_series(): void {
    const nextSeries = this.remainingSeries.splice(0, 8);
    this.displayedSeries = this.displayedSeries.concat(nextSeries);
  }

  show_series_details(seriesId: number): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = { seriesId };
    const dialogRef = this.dialog.open(SeriesDetailsComponent, dialogConfig);
  }
}