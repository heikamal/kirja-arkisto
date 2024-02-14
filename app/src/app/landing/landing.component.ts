import { ChangeDetectorRef, Component } from '@angular/core';
import { InitialLandingComponent } from '../initial-landing/initial-landing.component';
import { ProfileComponent } from '../profile/profile.component';
import { CommonModule } from '@angular/common';
import { SeriesComponent } from '../series/series.component';
import { AdminComponent } from '../admin/admin.component';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [InitialLandingComponent,ProfileComponent,SeriesComponent,AdminComponent,CommonModule],
  templateUrl: './landing.component.html',
})

export class LandingComponent {

  visible_component: string = 'initial';
  is_admin: boolean = true;

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
}
