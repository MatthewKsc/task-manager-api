import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor() { //dać router this.router.navigate([])
    //get auth token i set token setSession
    // setSession -> localStorage.setItem
    //logout localStorage.usuwamy wszystko
  }
}
