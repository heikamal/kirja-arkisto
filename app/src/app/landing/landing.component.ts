import { ChangeDetectorRef, Component } from '@angular/core';
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
@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [InitialLandingComponent,ProfileComponent,SeriesComponent,BooksComponent,AdminComponent,CommonModule, 
    MyBooksComponent, MyProfileComponent, BookDetailsComponent, SearchComponent, PhotoComponent],
  templateUrl: './landing.component.html',
})

export class LandingComponent {

  visible_component: string = 'initial';
  is_admin: boolean = true;

  mybooks: boolean = false;
  myprofile: boolean = false;


  show_profile() {
    event?.preventDefault();
    this.visible_component = 'profile'
    this.mybooks = false;
    this.myprofile = false;
  }

  show_series() {
    event?.preventDefault();
    this.visible_component = 'series'
    this.mybooks = false;
    this.myprofile = false;
  }
  show_admin() {
    event?.preventDefault();
    this.visible_component = 'admin'
    this.mybooks = false;
    this.myprofile = false;
  }
  show_initial() {
    event?.preventDefault();
    this.visible_component = 'initial'
    this.mybooks = false;
    this.myprofile = false;
  }
  show_books() {
    event?.preventDefault();
    this.visible_component = 'books'
    this.mybooks = false;
    this.myprofile = false;
  }

  show_my_profile() {
    this.visible_component = 'landing'
    this.mybooks = true;
    this.myprofile = false;
  }
  show_my_books() {
    this.visible_component = 'landing'
    this.myprofile = true;
    this.mybooks = false;
  }
  show_search() {
    event?.preventDefault();
    this.visible_component = 'search'
    this.mybooks = false;
    this.myprofile = false;
  }
  show_photo() {
    event?.preventDefault();
    this.visible_component = 'photo'
    this.mybooks = false;
    this.myprofile = false;
  }

}
