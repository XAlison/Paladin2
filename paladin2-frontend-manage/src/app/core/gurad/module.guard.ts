import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { AuthService } from '../../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ModuleGuard implements CanActivate, CanLoad {

  constructor(private authService: AuthService, private router: Router) {
  }

  canLoad(route: Route, segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    console.log('ModuleGuard-canLoad-route', route);
    console.log('ModuleGuard-canLoad-segments', segments);
    return true;
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    console.log('ModuleGuard-canActivate-stare.url', state.url);
    return this.authService.getUiPermission().pipe(
      switchMap(permissions => {
        for (const moduleNav of permissions.uiNavs) {
          if (moduleNav.path === state.url && moduleNav.children.length > 0) {
            this.router.navigate([moduleNav.children[0].path], {replaceUrl: true});
          }
        }
        return of(true);
      })
    );
  }
}
