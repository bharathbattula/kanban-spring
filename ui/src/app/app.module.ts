import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {LoginComponent} from './login/login.component';
import {MatInputModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HomeComponent} from './home/home.component';
import {HeaderComponent} from './home/header/header.component';
import {SidenavComponent} from './home/sidenav/sidenav.component';
import {RequestInterceptor} from './helper/request.interceptor';
import {CookieService} from 'ngx-cookie-service';
import {FlexLayoutModule} from '@angular/flex-layout';
import {BoardComponent} from './board/board.component';
import {AddProjectComponent} from './dialog/add-project/add-project.component';
import {TaskCardComponent} from './board/task-card/task-card.component';
import {ListComponent} from './board/list/list.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {ErrorInterceptor} from "./helper/error.interceptor";
import {ConfirmationComponent} from "./dialog/confirmation/confirmation.component";
import {TaskDetailComponent} from './board/task-detail/task-detail.component';
import { ProjectSettingComponent } from './project-setting/project-setting.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    SidenavComponent,
    BoardComponent,
    AddProjectComponent,
    TaskCardComponent,
    ListComponent,
    ConfirmationComponent,
    TaskDetailComponent,
    ProjectSettingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatInputModule,
    ReactiveFormsModule,
    HttpClientModule,
    FlexLayoutModule,
    MatAutocompleteModule,
    FormsModule,

  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptor,
      multi: true,
    }, {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    },
    CookieService,
  ],
  entryComponents: [
    AddProjectComponent,
    ConfirmationComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
