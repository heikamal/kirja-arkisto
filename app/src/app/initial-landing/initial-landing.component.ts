import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CarouselComponent } from '../carousel/carousel.component';


@Component({
  selector: 'app-initial-landing',
  standalone: true,
  imports: [CarouselComponent],
  templateUrl: './initial-landing.component.html'
})
export class InitialLandingComponent{
}
