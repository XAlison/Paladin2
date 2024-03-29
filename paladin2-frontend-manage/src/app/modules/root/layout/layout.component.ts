import { Component, HostBinding, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { ChangePasswordComponent } from '../change-password/change-password.component';

@Component({
  selector: 'pld-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.less']
})
export class LayoutComponent implements OnInit {

  @HostBinding('class.unfold') folding = false;
  @ViewChild('changePasswordModal') changePasswordModal: ChangePasswordComponent;
  rootNavs = [
    // { path: '/home', title: '首页', tag: 'home' },
    // { path: '/user', title: '用户', tag: 'user' },
    // { path: '/shop', title: '店铺', tag: 'shop' },
    // { path: '/order', title: '订单', tag: 'order' },
    // { path: '/goods', title: '商品', tag: 'goods' },
    // { path: '/config', title: '配置', tag: 'config' },
  ];
  subNavs = [
    // { path: '/home/1', title: '二级导航1', tag: 'home' },
    // { path: '/home/2', title: '二级导航2', tag: 'home' },
    // { path: '/home/3', title: '二级导航3', tag: 'home' },
    // { path: '/home/4', title: '二级导航4', tag: 'home' },
  ];
  adminNickName: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
  ) {
    this.authService.getUiPermission().subscribe(uiPermission => {
      this.rootNavs = uiPermission.uiNavs;
    });
    this.router.events
      .pipe(filter(evt => evt instanceof NavigationEnd))
      .subscribe((evt: NavigationEnd) => {
        const rootPath = '/' + evt.urlAfterRedirects.split('/')[1];
        for (const rootNavNode of this.rootNavs) {
          if (rootNavNode.path === rootPath) {
            this.subNavs = rootNavNode.children;
            break;
          }
        }
      });
    this.adminNickName = this.authService.getNickName();
  }

  ngOnInit() {
  }

  toggle() {
    this.folding = !this.folding;
  }

  logout() {
    this.authService.logout();
  }

  changePassword() {
    this.changePasswordModal.open();
  }
}
