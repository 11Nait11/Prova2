import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ServerService } from './server.service';
import { waitForAsync } from '@angular/core/testing';
import { Location } from '@angular/common';
import { User } from './classes';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  loggedIn: boolean = false;
  sessionId:string = "";
  user: User = new User();

  constructor(private service : ServerService, private location: Location){}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    const urlParams = new URLSearchParams(window.location.search);
    let id = urlParams.get('sessionId');
    if(id != null && id != ""){
      this.service.checkLoggedIn(id).subscribe(loggedIn => this.loggedIn = loggedIn).add( () => {
        if(this.loggedIn && id != null && id != ""){
          this.service.getUserBySession(id).subscribe(user => this.user = user).add( () => {
            if(this.loggedIn && id != null && id != ""){
              this.setSessionId(id);
              if(this.user.name == undefined){
                this.loggedIn = false;
                window.location.replace("http://localhost:4200/");
                alert("Login Scaduto")
              }
            }
          });
        }
      });

    }

  }

  title = 'CasaSubito.it';

  public getLoggedIn(): boolean {
    return this.loggedIn;
  }

  public getSessionId(): string{
    return this.sessionId;
  }

  public getUser(): User{
    return this.user;
  }

  private setSessionId(id: string){
    this.sessionId = id;
  }

}


