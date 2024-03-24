import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { InitialLandingComponent } from '../initial-landing/initial-landing.component';
import { ProfileComponent } from '../profile/profile.component';
import { CommonModule } from '@angular/common';
import { SeriesComponent } from '../series/series.component';
import { AdminComponent } from '../admin/admin.component';
import { BooksComponent } from '../books/books.component';
import { MyBooksComponent } from '../my-books/my-books.component';
import { MyProfileComponent } from '../my-profile/my-profile.component';
import { BookDetailsComponent } from '../book-details/book-details.component';
import { SearchComponent } from '../search/search.component';
import { PhotoComponent } from '../photo/photo.component';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [InitialLandingComponent, ProfileComponent, SeriesComponent, BooksComponent, AdminComponent, CommonModule,
    MyBooksComponent, MyProfileComponent, BookDetailsComponent, SearchComponent, PhotoComponent],
  templateUrl: './landing.component.html',
})

export class LandingComponent implements OnInit {

  constructor(private cookieService: CookieService) { }

  visible_component: string = 'initial';
  is_admin: boolean = false;
  
  ngOnInit(): void {
  const roles_cookie = this.cookieService.get('roles');
  this.is_admin = roles_cookie.includes("ROLE_ADMIN")
  }

  show_profile() {
    event?.preventDefault();
    this.visible_component = 'profile'
  }

  show_series() {
    event?.preventDefault();
    this.visible_component = 'series'
  }
  show_admin() {
    event?.preventDefault();
    this.visible_component = 'admin'
  }
  show_initial() {
    event?.preventDefault();
    this.visible_component = 'initial'
  }
  show_books() {
    event?.preventDefault();
    this.visible_component = 'books'
  }
  show_search() {
    event?.preventDefault();
    this.visible_component = 'search'
  }
  show_photo() {
    event?.preventDefault();
    this.visible_component = 'photo'
  }
}
