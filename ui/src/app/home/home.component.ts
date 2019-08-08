import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material";
import {HeaderComponent} from "./header/header.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  @ViewChild('sidenav', {static: true}) sideNavigationEle: MatSidenav;
  @ViewChild('appHeader', {static: true}) appHeader: HeaderComponent;

  mode: string;
  opened: boolean;

  mediaQuery: MediaQueryList;

  constructor() {
  }

  ngOnInit() {
  }

}
