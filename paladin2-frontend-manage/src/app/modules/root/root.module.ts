import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { NgZorroAntdModule } from 'ng-zorro-antd';

@NgModule({
  declarations: [LoginComponent, NotFoundComponent, HomeComponent, LayoutComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    NgZorroAntdModule,
  ]
})
export class RootModule { }
