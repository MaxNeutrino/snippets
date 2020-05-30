import {Component, OnInit} from '@angular/core';
import {webSocket} from 'rxjs/webSocket';

export class Event {
  name: string;
  count: number;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'websocket-angular';

  messages: string[] = [];

  private subject = webSocket('ws://localhost:8080/push');

  ngOnInit(): void {
    this.subject.next({message: 'message'}); // <- ping first message
    this.subject.subscribe(message => {       // <- listen messages from server
      const event = message as Event;
      this.messages.push(event.name + ' #' + event.count);
    });
  }
}
