import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Order } from '../classes/order';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private activeOrdersUrl = environment.activeOrders;
  private orderUrl = environment.orders;

  constructor(private http: HttpClient) { }

  public getAllActiveOrdersForRestaurant(id: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.activeOrdersUrl}/${id}`);
  }

  public updateOrderStatus(order: Order): Observable<Order> {
    return this.http.patch<Order>(`${this.orderUrl}/progressByOne/${order.order_id}`, "");
  }
}
