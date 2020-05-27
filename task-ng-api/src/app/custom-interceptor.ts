import {Injectable} from "@angular/core";
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError, finalize, retry} from "rxjs/operators";
import {AuthServiceService} from "./services/auth-service.service";

@Injectable()
export class CustomInterceptor implements HttpInterceptor{

  constructor(private authService: AuthServiceService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if(this.authService.Token){
      req = req.clone({
        setHeaders: {
          'Authorization': `${this.authService.Token}`
        }
      })
    }

    console.log(req.headers.get('authorization'))
    return next.handle(req)
      .pipe(
        // Retry on failure
        retry(2),

        // Handle errors
        catchError((error: HttpErrorResponse) => {
          alert(`HTTP Error: ${req.url}`);
          return throwError(error);
        }),

        // PROFILING
        finalize(() => {
          const profilingMsg = `${req.method} "${req.urlWithParams}"`;
          console.log(profilingMsg);
        })
      );
  }


}
