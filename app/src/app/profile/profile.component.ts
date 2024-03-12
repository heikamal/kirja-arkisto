import { Component, EventEmitter, Output } from '@angular/core';
import { MyProfileComponent } from '../my-profile/my-profile.component';
import { MyBooksComponent } from '../my-books/my-books.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MyProfileComponent, MyBooksComponent],
  templateUrl: './profile.component.html'
})

export class ProfileComponent {
  @Output("myprofile") my_profile: EventEmitter<any> = new EventEmitter();
@Output("mybooks") my_books: EventEmitter<any> = new EventEmitter();
  show_my_profile() {
    this.my_profile.emit();
  }
  show_my_books() {
    this.my_books.emit();
  }
}
