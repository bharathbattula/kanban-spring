import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {Injectable} from "@angular/core";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor{

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError((err => {
      if (err.status === 401) {
        //logout;
        location.reload(true);
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    })));
  }

}
