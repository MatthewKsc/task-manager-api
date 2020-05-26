import {CanActivate} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthServiceService} from "./auth-service.service";

@Injectable()
export class AuthGuard implements CanActivate{

  constructor(private authService: AuthServiceService) {
  }

  canActivate(){
    if (this.authService){ //isAuthenticated
      return true;
    }else {
      this.authService //login method
    }
  }
}
