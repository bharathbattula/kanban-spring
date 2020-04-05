import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Injectable} from "@angular/core";
import {RestService} from "../rest.service";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private rest: RestService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError((err => {
      if (err.status === 401) {
        this.rest.logout();
        location.reload(true);
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    })));
  }

}
