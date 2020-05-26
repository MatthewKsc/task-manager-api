import { Component, OnInit } from '@angular/core';
import {AuthServiceService} from "../../services/auth-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  constructor(private authService : AuthServiceService, private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      res=> {
        this.authService.isLogged=true;
        //getCurrentUser
        this.router.navigateByUrl('/task')
      },
      error => {
        alert("Error occurred while login in")
      }
    )
  }
}
