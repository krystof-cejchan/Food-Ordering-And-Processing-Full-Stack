import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrdersOverviewComponent } from './orders-overview/orders-overview.component';

const routes: Routes = [{ path: '', redirectTo: 'orders', pathMatch: 'full' },
{ path: 'orders', component: OrdersOverviewComponent },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
