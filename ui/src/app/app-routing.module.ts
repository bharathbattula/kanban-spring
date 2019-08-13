import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'app', component: HomeComponent},
  {path: '', redirectTo: "/login", pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(
    routes,
    {enableTracing: false}
  )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
