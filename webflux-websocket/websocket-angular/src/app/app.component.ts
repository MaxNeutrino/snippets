import {Component, OnInit} from '@angular/core';
import {webSocket} from 'rxjs/webSocket';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'websocket-angular';

  messages: string[] = [];

  private subject = webSocket({
    url: 'ws://localhost:8080/push',
    deserializer: e => e.data
  });

  ngOnInit(): void {
    this.subject.next({message: 'message'});
    this.subject.subscribe(message => {
      this.messages.push(message as string);
    });
  }
}
