import {CanActivate, Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthServiceService} from "./auth-service.service";

@Injectable()
export class AuthGuard implements CanActivate{

  constructor(private authService: AuthServiceService, private router: Router) {
  }

  canActivate(){
    if (this.authService.isLogged){ //isAuthenticated
      return true;
    }else {
      this.router.navigateByUrl('/login') //login method
    }
  }
}
