import {ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from '@angular/material';
import {HeaderComponent} from './header/header.component';
import {MediaObserver} from '@angular/flex-layout';
import {MediaMatcher} from '@angular/cdk/layout';
import {RestService} from '../shared/services/rest.service';
import {Router} from '@angular/router';
import {DataService} from '../shared/services/data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  @ViewChild('sidenav', {static: true}) sideNavigationEle: MatSidenav;
  @ViewChild('appHeader', {static: true}) appHeader: HeaderComponent;

  mode: string;
  opened: boolean;

  mediaQuery: MediaQueryList;

  private _mediaQueryListner: () => void;

  constructor(public media: MediaObserver,
              changeDectorRef: ChangeDetectorRef,
              mediaMatcher: MediaMatcher,
              private rest: RestService,
              private router: Router,
              private dataService: DataService) {
    this.mediaQuery = mediaMatcher.matchMedia('(max-width: 600px)');
    this._mediaQueryListner = () => changeDectorRef.detectChanges();
    this.mediaQuery.addListener(this._mediaQueryListner);

  }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.mediaQuery.removeListener(this._mediaQueryListner);
  }
}
