import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, mergeMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd';

/**
 * 处理常规的一些请求响应异常
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private messageService: NzMessageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      mergeMap((res) => {
        // 去掉注释，可以查看下错误对象
        // console.log(res);
        return of(res);
      }),
      catchError((err: HttpErrorResponse) => this.handleError(err)),
    );
  }

  private handleError(err: HttpErrorResponse): Observable<any> {
    console.log(err);
    // 业务处理：一些通用操作
    switch (err.status) {
      case 400:
        this.messageService.error(err.error.message);
        break;
      case 401: // 未登录状态码
        this.router.navigate(['./login']);
        break;
      case 403:
        this.messageService.error('接口未授权，无法访问');
        break;
      case 404:
        this.messageService.error('接口不存在或已废弃');
        break;
      case 405:
        this.messageService.error('接口Method不支持');
        break;
      case 500:
        this.messageService.error('服务器繁忙');
        break;
      default:
        this.messageService.error('未知错误');
        break;
    }
    throw err;
  }
}
