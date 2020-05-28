import { Component, OnInit } from '@angular/core';
import {AuthServiceService} from "../services/auth-service.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public authService: AuthServiceService) { }

  ngOnInit(): void {

  }

  logout(){
    this.authService.logout();
  }
}
