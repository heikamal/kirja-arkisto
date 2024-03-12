import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { Book } from '../book';

@Component({
  selector: 'app-book-details',
  standalone: true,
  imports: [CommonModule],
  providers: [DataService],
  templateUrl: './book-details.component.html'
})
export class BookDetailsComponent implements OnInit {
title: any;
author: any;
date: any;
series: any;
description: any;
series_number: any;

  constructor(private dataService: DataService, private dataService2: DataService ) { }
  
  ngOnInit(): void {
    this.dataService.get_book("1").subscribe((response: any[]) => {
      const jsonStr: string = JSON.stringify(response);
      const jsonObject: any = JSON.parse(jsonStr);
     this.title = jsonObject.nimi;
     this.author = jsonObject.kirjailija;
     this.date = jsonObject.julkaisuVuosi;
    this.series_number = jsonObject.kirjaSarja;
    this.description = jsonObject.kuvaus;
    });

    this.dataService2.get_series_info(this.series_number).subscribe((response: any[]) => {
      const jsonStr2: string = JSON.stringify(response);
      const jsonObject2: any = JSON.parse(jsonStr2);
     this.series = jsonObject2.title;
    });

  }


}
