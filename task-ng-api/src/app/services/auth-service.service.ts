import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ApiServiceService} from "./api-service.service";
import {TaskComponent} from "../components/task/task.component";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private BASIC_URL = "http://localhost:8082";
  private POST_LOGIN= `${this.BASIC_URL}\\login`
  isLogged: boolean;
  Token: string;
  userId: string;

  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string) {
    return this.http.post(this.POST_LOGIN, JSON.stringify({
      "username": username,
      "password": password
      }), {observe: 'response'})
      .subscribe(
        resp=>{
          this.Token = resp.headers.get('authorization');
          this.userId = resp.headers.get(`UserId`);
          // console.log(this.userId);
          // console.log(this.Token);
          this.isLogged = true;
          this.router.navigateByUrl('/task');
        },
        error => {
          alert("Error while login to service");
        });
  }
}
