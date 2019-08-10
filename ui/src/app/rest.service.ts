import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private host = "http://localhost:8080";

  private TOKEN = "bearer";


  constructor(private http:HttpClient) { }

  request(requestBody:any, endpoint:string, requestMethod:string):Promise<any> {

    switch (requestMethod) {
      case "POST":
        return this.http.post(this.host + "/" + endpoint, requestBody,)
          .toPromise()
          .then(response => response);

      case "GET":
        return this.http.get(this.host + "/" + endpoint, {withCredentials: true})
          .toPromise()
          .then(response => response);
    }
  }

  setToken(token: string) {
    localStorage.setItem(this.TOKEN, token);
  }

  getToken(): string {
    return localStorage.getItem(this.TOKEN);
  }

}
