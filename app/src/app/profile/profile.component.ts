import { Component, EventEmitter, Output } from '@angular/core';
import { MyProfileComponent } from '../my-profile/my-profile.component';
import { MyBooksComponent } from '../my-books/my-books.component';
import { CookieService } from 'ngx-cookie-service';
import { DataService } from '../data.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MyProfileComponent, MyBooksComponent],
  providers: [DataService],
  templateUrl: './profile.component.html'
})

export class ProfileComponent {
  username: any;
  bookshelf_data: any;

  constructor(private cookieService: CookieService, private data: DataService) { }

  ngOnInit() {
    this.username = this.cookieService.get('user');
    this.get_bookshelf();
  }
  @Output("myprofile") my_profile: EventEmitter<any> = new EventEmitter();
  @Output("mybooks") my_books: EventEmitter<any> = new EventEmitter();
  show_my_profile() {
    this.my_profile.emit();
  }
  show_my_books() {
    this.my_books.emit();
  }
  get_bookshelf() {
    this.data.get_bookshelf().subscribe(response => {
      this.bookshelf_data = response;
      console.log('Fetched Data:', this.bookshelf_data);
    })
  }
}
