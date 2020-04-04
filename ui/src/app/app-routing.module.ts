import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {BoardComponent} from "./board/board.component";
import {AuthGuard} from "./helper/auth.guard";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {
    path: 'app', component: HomeComponent, canActivate: [AuthGuard],
    children: [
      {path: 'project/:name', component: BoardComponent}
    ]
  },
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
