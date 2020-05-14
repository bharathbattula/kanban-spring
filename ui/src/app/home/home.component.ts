import {Component, ViewChild} from '@angular/core';
import {HeaderComponent} from './header/header.component';
import {MediaObserver} from '@angular/flex-layout';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  @ViewChild('appHeader', {static: true}) appHeader: HeaderComponent;

  constructor(public media: MediaObserver) {
  }

}
