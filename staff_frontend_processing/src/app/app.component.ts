import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  public now: String = new Date().toLocaleString();

  constructor() {
      setInterval(() => {
        this.now = new Date().toLocaleString();
      }, 1);
  }}
