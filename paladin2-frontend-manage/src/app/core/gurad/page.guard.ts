import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { NzMessageService } from 'ng-zorro-antd';
import { AuthService } from '../../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PageGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: NzMessageService,
  ) {
  }

  // 判断路由是否可以访问
  // https://www.kancloud.cn/wujie520303/angular2_note/243673
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<boolean> | Promise<boolean> | boolean {
    console.log('PageGuard-canActivate-state', state);
    console.log('PageGuard-canActivate-next', next);
    // 过滤url中?后面的字符串
    // 过滤url中;后面的字符串(Angular的一种传参方式)
    const nextUrl = state.url.split(';')[0].split('?')[0];
    console.log('PageGuard-canActivate-nextUrlWithOutParams', nextUrl);
    return this.checkToken().pipe(
      switchMap(checkResult => checkResult ?
        this.authService.getUiPermission().pipe(
          switchMap(uiPermission => {
            const isAllow = uiPermission.uiPaths.indexOf(nextUrl) !== -1;
            if (!isAllow) {
              this.messageService.error('页面未授权，无法访问');
            }
            return of(isAllow);
            // return of(true);
          })
        ) : of(false)
      )
    );
  }

  checkToken(): Observable<boolean> {
    return new Observable((observer) => {
      const token = this.authService.getToken();
      if (!token) {
        observer.next(false);
        observer.complete();
        this.router.navigate(['/login']);
        return;
      } else {
        observer.next(true);
        observer.complete();
      }
    });
  }

}
