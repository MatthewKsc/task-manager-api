import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private BASIC_URL = "http://localhost:8082";
  private POST_LOGIN= `${this.BASIC_URL}\\login`
  isLogged: boolean;

  constructor(private http: HttpClient) { //dać router this.router.navigate([])
    //get auth token i set token setSession
    // setSession -> localStorage.setItem
    //logout localStorage.usuwamy wszystko
  }

  login(username: string, password: string) {
    return this.http.post(this.POST_LOGIN, JSON.stringify({
      "username": username,
      "password": password
      }));
  }
}
