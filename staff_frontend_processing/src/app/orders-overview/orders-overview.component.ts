import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrdersService } from "./orders.service";
import { SubSink } from "subsink";
import { MatSnackBar } from "@angular/material/snack-bar";
import { openSnackBar } from '../classes/snack-bar/snack-bar-component';
import { Order } from "../classes/order";
import { OrderStatus } from '../classes/order_status';



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
          this.orders = response.sort((a, b) => a.orderCreated < b.orderCreated ? -1 : 1);
        },
        error: () => openSnackBar(this.snackBar)
      }
    ));
  }

  orderImportanceColour(order: Order): string {
    switch (OrderStatus[order.orderStatus as keyof typeof OrderStatus]) {
      case OrderStatus.INIT, OrderStatus.SENT: return "info";
      case OrderStatus.BEING_PREPARED: return "primary";
      case OrderStatus.BEING_DELIVERED: return "warning";
      case OrderStatus.FINISHED: return "success";
      default: return "danger";
    }
  }

  updateStatus(order: Order, i: number): void {
    this.service.updateOrderStatus(order).subscribe({ next: (value: Order) => this.orders[i] = value });
  }

  public toLocalDate(date: Date):string{
    return date.toLocaleString();
  }
  

  ngOnDestroy(): void {
    this.subSink.unsubscribe()
  }

}
