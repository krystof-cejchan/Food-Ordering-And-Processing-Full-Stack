/*import { Injectable } from '@angular/core';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Stomp.Client;

  constructor() {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/ws';  // replace with your server URL
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.Stomp.over(ws);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/records/1', (message) => {
        if (message.body) {
          console.log('Received: ' + message.body);
        }
      });
    });
  }

  sendMessage(destination: string, body: any) {
    this.stompClient.send(destination, {}, JSON.stringify(body));
  }

  subscribeToTopic(topic: string, callback: (message: Stomp.Message) => void) {
    this.stompClient.subscribe(topic, callback);
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }
}
*/