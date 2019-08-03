import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RestService {

  host = "http://localhost:8080";
  constructor(private http:HttpClient) { }

  request(requestBody:any, endpoint:string, requestMethod:string):Promise<any> {

    switch (requestMethod) {
      case "POST":
        return this.http.post(this.host + "/" +endpoint, requestBody, {})
          .toPromise()
          .then(response => response);

      case "GET":
        return this.http.get(this.host + "/" +endpoint, {})
          .toPromise()
          .then(response => response);
    }
  }
}
