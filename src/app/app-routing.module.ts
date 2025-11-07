import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  //routing defined to go to the next page 
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full' 
      },
      {
        path: 'home',
        component: HomeComponent
      }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
