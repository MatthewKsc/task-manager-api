import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private BASIC_URL = "http://localhost:8082";
  private POST_LOGIN= `${this.BASIC_URL}\\login`
  isLogged: boolean;

  constructor(private http: HttpClient, private router: Router) {
    //get auth token i set token setSession
    // setSession -> localStorage.setItem
    //logout localStorage.usuwamy wszystko
  }

  login(username: string, password: string) {
    return this.http.post(this.POST_LOGIN, JSON.stringify({
      "username": username,
      "password": password
      }), {observe: 'response'})
      .subscribe(
        resp=>{
          console.log(resp.headers.get('authorization'))
          //getCurrentUser
          this.isLogged = true;
          this.router.navigateByUrl('/task');
        },
        error => {
          alert("Error while login to service")
        });
  }
}
