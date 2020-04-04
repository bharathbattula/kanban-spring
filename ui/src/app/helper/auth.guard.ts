import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {RestService} from "../rest.service";
import {Injectable} from "@angular/core";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private router:Router, private restService: RestService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const currentUser = this.restService.currentUserValue;

    if (currentUser) {
      return true;
    }

    this.router.navigate( ['/login'], { queryParams: { returnUrl: state.url }});
    return false;
  }

}
