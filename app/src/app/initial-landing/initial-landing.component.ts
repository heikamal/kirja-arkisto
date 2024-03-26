import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CarouselComponent } from '../carousel/carousel.component';
import {CookieService} from 'ngx-cookie-service';
import { Token } from '@angular/compiler';
import { LoginPageComponent } from '../login-page/login-page.component';
import { SeriesComponent } from '../series/series.component';

 
@Component({
  selector: 'app-initial-landing',
  standalone: true,
  imports: [CarouselComponent, LoginPageComponent,SeriesComponent],
  providers: [CookieService],
  templateUrl: './initial-landing.component.html'
})


export class InitialLandingComponent  {
}
