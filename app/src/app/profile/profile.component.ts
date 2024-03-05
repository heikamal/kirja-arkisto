import { Component } from '@angular/core';
import { MyProfileComponent } from '../my-profile/my-profile.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MyProfileComponent],
  templateUrl: './profile.component.html'
})
export class ProfileComponent {

}
