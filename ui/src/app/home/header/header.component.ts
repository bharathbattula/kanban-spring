import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatToolbar} from "@angular/material";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @ViewChild("appToolbar", {static:true}) appToolbar : MatToolbar;

  @Output() public sidenavToggle = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  toggleNavigation() {
    this.sidenavToggle.emit();
  }
}
