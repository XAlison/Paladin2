import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LoginSuccessInfo } from '../model/login-success-info';
import { switchMap, tap } from 'rxjs/operators';
import { UiPermission } from '../model/ui-permission';
import { Observable, of } from 'rxjs';

const STORAGE_KEY_TOKEN = 'Authorization';
const STORAGE_KEY_NICKNAME = 'NickName';

const apiUrls = {
  login: () => `/manage/passport/login`,
  logout: () => `/manage/passport/logout`,
  uiPermission: () => `/manage/sys/admin/ui_permission`,
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token = null;
  private nickName = null;
  private uiPermission: UiPermission = null;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
  ) {
  }

  login(params: { username: string, password: number }) {
    return this.httpClient.post<LoginSuccessInfo>(apiUrls.login(), params).pipe(
      tap(res => {
        // 1、保存到当前单例中
        this.token = res.token;
        this.nickName = res.nickName;
        // 2、保存到localStorage中
        localStorage.setItem(STORAGE_KEY_TOKEN, res.token);
        localStorage.setItem(STORAGE_KEY_NICKNAME, res.nickName);
      })
    );
  }

  getToken() {
    if (this.token == null) {
      this.token = localStorage.getItem(STORAGE_KEY_TOKEN);
    }
    return this.token;
  }

  getNickName() {
    if (this.nickName == null) {
      this.nickName = localStorage.getItem(STORAGE_KEY_NICKNAME);
    }
    return this.nickName;
  }

  logout() {
    this.httpClient.get(apiUrls.logout()).subscribe(() => {
      localStorage.removeItem(STORAGE_KEY_TOKEN);
      localStorage.removeItem(STORAGE_KEY_NICKNAME);
      this.router.navigate(['/login']);
    });
  }

  cleanUiPermission() {
    this.uiPermission = null;
  }

  getUiPermission(): Observable<UiPermission> {
    return of(this.uiPermission).pipe(
      switchMap((uiPermission) => {
        if (uiPermission === null) {
          return this.httpClient.get<UiPermission>(apiUrls.uiPermission()).pipe(
            tap(res => this.uiPermission = res)
          );
        } else {
          return of(uiPermission);
        }
      })
    );
  }
}
