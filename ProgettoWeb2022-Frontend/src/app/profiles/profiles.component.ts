import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ServerService } from '../server.service';
import { AppComponent } from '../app.component';
import { AfterContentChecked } from '@angular/core';
import { User } from '../classes';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {

  public sessionId = "";
  public loggedUser: User = new User();
  public user: User = new User();
  public profile: string = "";

  constructor(private app: AppComponent, private service: ServerService) {
  }

  ngOnInit(): void {
    const url = new URL(window.location.href);
    this.profile = url.pathname.split("/")[2];
    console.log(this.profile);
    this.service.getUserByNickname(this.profile).subscribe(user => this.user = user).add(()=>{
      console.log(this.user.role);
    })
  }

  ngAfterContentChecked(): void {
    //Called after every check of the component's or directive's content.
    //Add 'implements AfterContentChecked' to the class.
    this.sessionId = this.app.getSessionId();
    this.loggedUser = this.app.getUser();
    console.log(this.user.role);

  }

  public getSessionId():string{
    return this.app.getSessionId();
  }

  public getUser(): User{
    return this.app.getUser();
  }

  public getUserBanned(): boolean{
    return this.user.isBanned;
  }

  public getUserRole(): string{
    return this.user.role;
  }

  public updateUser(): void{
    let name = (<HTMLInputElement>document.getElementById("name")).value;
    let lastname = (<HTMLInputElement>document.getElementById("lastname")).value;
    let telephone = (<HTMLInputElement>document.getElementById("telephone")).value;
    let email = (<HTMLInputElement>document.getElementById("email")).value;
    let state = (<HTMLInputElement>document.getElementById("state")).value;
    let country = (<HTMLInputElement>document.getElementById("country")).value;
    let address = (<HTMLInputElement>document.getElementById("address")).value;
    let postalCode = (<HTMLInputElement>document.getElementById("postalCode")).value;
    let password = (<HTMLInputElement>document.getElementById("password")).value;
    let confirmPassword = (<HTMLInputElement>document.getElementById("confirmPassword")).value;

    if(password.length > 8 && password == confirmPassword){
      this.service.updateUser(this.user.nickname, name, lastname, telephone, email, state, country, address, postalCode, password).subscribe();
    }else if(password.length == 0 && confirmPassword.length == 0){
      this.service.updateUser(this.user.nickname, name, lastname, telephone, email, state, country, address, postalCode, "").subscribe();
    }else{
      alert("La password deve essere di almeno 8 caratteri e deve essere uguale a quella di conferma");
    }

  }

  public banUser(nickname: string): void{
    let res;
    this.service.banUser(nickname).subscribe(result => res = result);
  }

  public unBanUser(nickname: string): void{
    this.service.unbanUser(nickname).subscribe();
  }

  public makeAdmin(nickname: string): void{
    this.service.makeAdmin(nickname).subscribe();
  }
}
