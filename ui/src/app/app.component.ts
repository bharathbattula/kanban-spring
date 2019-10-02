import {Component, OnInit} from '@angular/core';
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(private iconRegistry: MatIconRegistry,
              private domSanitizer: DomSanitizer) {
    this.iconRegistry.addSvgIcon(
      'author-avatar',
      this.domSanitizer.bypassSecurityTrustResourceUrl('../assets/man.svg')
    )
  }
}
