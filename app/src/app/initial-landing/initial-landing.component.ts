import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { CarouselComponent } from '../carousel/carousel.component';
import {CookieService} from 'ngx-cookie-service';
import { Token } from '@angular/compiler';
import { LoginPageComponent } from '../login-page/login-page.component';

 
@Component({
  selector: 'app-initial-landing',
  standalone: true,
  imports: [CarouselComponent, LoginPageComponent],
  providers: [CookieService],
  templateUrl: './initial-landing.component.html'
})


export class InitialLandingComponent implements OnInit {

  
  painaus() {
    
  }
  ngOnInit(): void {
    
  }
}
