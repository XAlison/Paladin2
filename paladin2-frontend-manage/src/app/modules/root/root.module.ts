import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { ReactiveFormsModule } from '@angular/forms';
import { ChangePasswordComponent } from './change-password/change-password.component';

@NgModule({
  declarations: [LoginComponent, NotFoundComponent, HomeComponent, LayoutComponent, ChangePasswordComponent],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    NgZorroAntdModule,
    ReactiveFormsModule,
  ]
})
export class RootModule { }
