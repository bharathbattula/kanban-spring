import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserSession} from "./model/UserSession";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private host = "http://localhost:8080/api";

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
        return this.http.get(this.host + "/" + endpoint, {})
          .toPromise()
          .then(response => response);
    }
  }

  setSession(session: UserSession) {
    localStorage.setItem(this.CURRENT_USER, JSON.stringify(session));
    console.log(JSON.stringify(session));
  }

  getToken(): string {
    const session = this.getSession();
    console.log(session);
    if (session)
      return session.token;

    return null;
  }

  logout() {
    localStorage.removeItem(this.CURRENT_USER);
  }

  getSession() {
    return JSON.parse(localStorage.getItem(this.CURRENT_USER));
  }
}
