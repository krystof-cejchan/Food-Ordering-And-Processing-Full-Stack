import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OrdersOverviewComponent } from './orders-overview/orders-overview.component';
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RxStompService } from './rx_stomp/RxStompService';
import { rxStompServiceFactory } from './rx_stomp/RxStompFactory';


@NgModule({
  declarations: [
    AppComponent,
    OrdersOverviewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    NgbModule,],
  providers: [{
    provide: RxStompService,
    useFactory: rxStompServiceFactory,
  },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
