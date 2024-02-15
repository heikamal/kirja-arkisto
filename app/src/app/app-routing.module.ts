import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './landing/landing.component';
import { LoginPageComponent } from './login-page/login-page.component';
const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: "/./login.component"},
  {path: "/./landing", component: LandingComponent},
  {path: "./login-page", component: LoginPageComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
