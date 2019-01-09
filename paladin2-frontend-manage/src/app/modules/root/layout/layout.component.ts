import { Component, HostBinding, OnInit } from '@angular/core';

@Component({
  selector: 'pld-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.less']
})
export class LayoutComponent implements OnInit {

  @HostBinding('class.unfold') folding = false;
  rootNavs = [
    { path: '/home', title: '首页', tag: 'home' },
    { path: '/user', title: '用户', tag: 'user' },
    { path: '/shop', title: '店铺', tag: 'shop' },
    { path: '/order', title: '订单', tag: 'order' },
    { path: '/goods', title: '商品', tag: 'goods' },
    { path: '/config', title: '配置', tag: 'config' },
  ];
  subNavs = [
    { path: '/home/1', title: '二级导航1', tag: 'home' },
    { path: '/home/2', title: '二级导航2', tag: 'home' },
    { path: '/home/3', title: '二级导航3', tag: 'home' },
    { path: '/home/4', title: '二级导航4', tag: 'home' },
  ];

  constructor() {
  }

  ngOnInit() {
  }

  toggle() {
    this.folding = !this.folding;
  }

  logout() {
    // TODO 退出登录
  }

  changePassword() {
    // TODO 修改密码
  }
}
