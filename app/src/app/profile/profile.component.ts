import { Component, EventEmitter, Output } from '@angular/core';
import { MyProfileComponent } from '../my-profile/my-profile.component';
import { MyBooksComponent } from '../my-books/my-books.component';
import { CookieService } from 'ngx-cookie-service';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { MySeriesComponent } from '../my-series/my-series.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MyProfileComponent, MyBooksComponent, CommonModule, MySeriesComponent],
  providers: [DataService],
  templateUrl: './profile.component.html'
})

export class ProfileComponent {
  username: any;
  bookshelf_data: any;
  subcomponent: string = '';

  constructor(private cookieService: CookieService, private data: DataService) { }

  ngOnInit() {
    this.username = this.cookieService.get('user');
    this.get_bookshelf();
  }
  @Output("myprofile") my_profile: EventEmitter<any> = new EventEmitter();
  @Output("mybooks") my_books: EventEmitter<any> = new EventEmitter();
  show_my_profile() {
    event?.preventDefault();
    this.subcomponent = 'my_profile'
  }
  show_my_books() {
    event?.preventDefault();
    this.subcomponent = 'my_books'
  }
  show_my_series() {
    event?.preventDefault();
    this.subcomponent = 'my_series'
  }
  get_bookshelf() {
    this.data.get_bookshelf().subscribe(response => {
      this.bookshelf_data = response;
      console.log('Fetched Data:', this.bookshelf_data);
    })
  }
}
