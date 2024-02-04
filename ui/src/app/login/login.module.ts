import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SMMaterialModule } from '../sm.material.module';
import { LoginComponent } from './login.component';
import { LoginRoutes } from './login.routing';

@NgModule({
  imports: [
    CommonModule,
    SMMaterialModule,
    RouterModule.forChild(LoginRoutes),
    LoginComponent,
  ],
})
export class LoginModule { }
