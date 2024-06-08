import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Order } from '../classes/order';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  private serverUrl = environment.activeOrders;

  constructor(private http: HttpClient) { }

  public getAllActiveOrdersForRestaurant(id: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.serverUrl}/${id}`);
  }
}
