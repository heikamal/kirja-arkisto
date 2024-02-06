import { Component } from '@angular/core';
import { LandingComponent } from "./landing/landing.component";

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    imports: [LandingComponent]
})
export class AppComponent {
  title = 'app';
}
