import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './modules/root/login/login.component';
import { NotFoundComponent } from './modules/root/not-found/not-found.component';
import { HomeComponent } from './modules/root/home/home.component';
import { LayoutComponent } from './modules/root/layout/layout.component';
import { PageGuard } from './core/gurad/page.guard';
import { ModuleGuard } from './core/gurad/module.guard';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent, canActivate: [PageGuard] },
      { path: 'config', loadChildren: './modules/config/config.module#ConfigModule', canLoad: [ModuleGuard], canActivate: [ModuleGuard] },
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', pathMatch: 'full', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    useHash: true
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
