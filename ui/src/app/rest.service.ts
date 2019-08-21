import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserSession} from "./model/UserSession";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private host = "http://localhost:8080";

  private TOKEN = "bearer";
  private CURRENT_USER = "_current-user";

  constructor(private http:HttpClient) { }

  request(requestBody:any, endpoint:string, requestMethod:string):Promise<any> {

    switch (requestMethod) {
      case "POST":
        return this.http.post(this.host + "/" + endpoint, requestBody)
          .toPromise()
          .then(response => response);

      case "GET":
        return this.http.get(this.host + "/" + endpoint, {withCredentials: true})
          .toPromise()
          .then(response => response);
    }
  }

  setSession(session: UserSession) {
    localStorage.setItem(this.CURRENT_USER, JSON.stringify(session));
  }

  getToken(): string {
    const session = JSON.parse(localStorage.getItem(this.CURRENT_USER));

    if (session)
      return session[this.TOKEN];

    return null;
  }

  logout() {
    localStorage.removeItem(this.CURRENT_USER);
  }
}
