import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {CookieService} from "ngx-cookie-service";
import {RestService} from "../rest.service";

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService, private rest: RestService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // const token  = this.cookieService.get('token');

    // console.log(`Cookie:${token}`);

    const token = this.rest.getToken();

    if(token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req);

  }

}
