import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrdersService } from "./orders.service";
import { SubSink } from "subsink";
import { MatSnackBar } from "@angular/material/snack-bar";
import { openSnackBar } from '../classes/snack-bar/snack-bar-component';
import { Order } from '../classes/order';



@Component({
  selector: 'app-orders-overview',
  templateUrl: './orders-overview.component.html',
  styleUrls: ['./orders-overview.component.sass']
})
export class OrdersOverviewComponent implements OnInit, OnDestroy {
  private subSink: SubSink = new SubSink();
  public orders: Order[] = [];

  constructor(private service: OrdersService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.subSink.add(this.service.getAllActiveOrdersForRestaurant(1).subscribe(
      {
        next: async (response: Order[]) => {
          const msToWait = 400, msMaxToWait = 5000, currMs = Date.now();
          while (response == undefined && (Date.now() - currMs) < msMaxToWait) {
            await new Promise(f => setTimeout(f, msToWait));
          }
          this.orders = response;
          console.log(this.orders);

        },
        error: () => openSnackBar(this.snackBar),
      }
    ));
  }

  ngOnDestroy(): void {
    this.subSink.unsubscribe()
  }

}
