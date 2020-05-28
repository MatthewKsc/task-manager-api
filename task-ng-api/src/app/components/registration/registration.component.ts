import { Component, OnInit } from '@angular/core';
import {User} from "../task/models/user";
import {AuthServiceService} from "../../services/auth-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  username: string;
  password: string;
  email: string;

  constructor(private authService: AuthServiceService, private router: Router) { }

  ngOnInit(): void {
  }

  registerUser() {
    let user: User={
      username: this.username,
      password: this.password,
      email: this.email
    }
    this.authService.register(user).subscribe(
      res=>{
        this.router.navigateByUrl('/login')
      })
  }
}
