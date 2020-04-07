import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserSession} from "../model/UserSession";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private host = "http://localhost:8080/api";

  private currentUserSubject: BehaviorSubject<UserSession>;
  public currentUser: Observable<UserSession>;

  private CURRENT_USER = "_current-user";

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<UserSession>(JSON.parse(localStorage.getItem(this.CURRENT_USER)));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): UserSession {

    return this.currentUserSubject.getValue();
  }

  public set currentUserValue(userSession) {
    localStorage.setItem(this.CURRENT_USER, JSON.stringify(userSession));
    this.currentUserSubject.next(userSession);
  }

  public request(requestBody: any, endpoint: string, requestMethod: string): Promise<any> {

    switch (requestMethod) {
      case 'POST':
        return this.http.post(this.host + "/" + endpoint, requestBody)
          .toPromise()
          .then(response => response)
          .catch(reason => {
            if (reason.status === 401) {
              this.router.navigateByUrl('/login');
            }
          });

      case 'GET':
        return this.http.get(this.host + "/" + endpoint, {})
          .toPromise()
          .then(response => response);

      case 'PUT':
        return this.http.put(this.host + "/" + endpoint, requestBody, {})
          .toPromise()
          .then(response => response);

      case 'DELETE' :
        return this.http.delete(this.host + '/' + endpoint, {})
          .toPromise()
          .then(response => response);
    }
  }

  setSession(userSession: UserSession) {
    localStorage.setItem(this.CURRENT_USER, JSON.stringify(userSession));
    this.currentUserSubject.next(userSession);
  }

  getToken(): string {

    const userSession = this.currentUserSubject.getValue();

    if (userSession && userSession.token) {
      return userSession.token;
    }

    return null;
  }

  logout() {
    localStorage.removeItem(this.CURRENT_USER);
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

}
