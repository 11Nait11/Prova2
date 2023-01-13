import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ad, Property } from '../classes';
import { User } from '../classes';
import { ServerService } from '../server.service';
import { AppComponent } from '../app.component';
import { Renderer2 } from '@angular/core';
import { retry } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GoogleMapsModule } from '@angular/google-maps';
import { Observable, catchError, map, of } from 'rxjs';
import { FacebookService, InitParams, LoginResponse, UIParams, UIResponse } from 'ngx-facebook';


@Component({
  selector: 'app-ad-details',
  templateUrl: './ad-details.component.html',
  styleUrls: ['./ad-details.component.css']
})
export class AdDetailsComponent {

  public ad: Ad = new Ad();
  public id: string = "";
  public owner: User = new User();
  public user: User = new User();
  public sessionId: string | null = "";
  public blobs: Blob[] = [];

  public adId:number = 0;
  public display:Boolean=false;
  public displayRece:Boolean=false;
  public changed: boolean = false;
  public property!: Property;
  apiLoaded: Observable<boolean> | undefined;
  mapLoaded: boolean | undefined;
  noImage: boolean = false;

  //API GOOGLE MAPS
  public lat: string ="";
  public lng: string ="";
  center: google.maps.LatLngLiteral = {lat: 41.97707664579145, lng: 12.439453124999996};
  zoom = 5;
  markerOptions: google.maps.MarkerOptions = {draggable: false};
  markerPositions: google.maps.LatLngLiteral[] = [];
  public url: string = "";

  constructor(private service: ServerService, private app: AppComponent, private renderer: Renderer2, private httpClient: HttpClient, private fb: FacebookService) {
    this.apiLoaded = httpClient.jsonp("https://maps.googleapis.com/maps/api/js?key=AIzaSyCugDwETlCONJNbxQRQ7Qv5OwsD6bv98yY", "callback")
      .pipe(
        map(() => true),
        catchError(() => of(false)),
      );

      const initParams: InitParams = {
        appId: '560352242616863',
        xfbml: true,
        version: 'v2.8'
      };

      fb.init(initParams);
  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    const url = new URL(window.location.href);
    this.url = url.href;
    this.id = url.pathname.split("/")[2];
    let session: string | null;
    session = url.searchParams.get("sessionId");
    this.service.getAd(this.id).subscribe((ad: Ad) => {
      this.ad = ad;
      console.log(this.ad.auction);
      this.owner = ad.user;
      this.adId = ad.id;
      this.sessionId = session;
      this.service.getImage(this.adId).subscribe(blobList => {
        this.blobs = blobList;
        let carosello = document.getElementById("carosello") as HTMLDivElement;
        console.log(carosello);
        this.noImage = true;
        for(let i = 0 ; i < this.blobs.length; i++){
          let div = this.renderer.createElement("div") as HTMLDivElement;
          let img = this.renderer.createElement("img") as HTMLImageElement;
          div.className = "carousel-item";
          img.className = "d-block w-100 img-fluid";
          img.style.height = "400px";
          if(i == 0){
            div.className += " active";
          }
          img.src = 'data:image/jpeg;base64,' + this.blobs[i];
          div.appendChild(img);
          carosello.appendChild(div);
          console.log(div);
          console.log(img);
          this.noImage = false;
        }
      }).add(() =>{
        this.service.getUserBySession(session).subscribe(user => this.user = user).add(() => {
          console.log("ID PROPRIETà: " + this.ad.property);
          this.service.getProperty(this.ad.property).subscribe(property => {
            this.property = property
            this.lat = property.latitude;
            this.lng = property.longitude;
            this.markerPositions.push({lat: parseFloat(this.lat), lng: parseFloat(this.lng)});
            this.center = {lat: parseFloat(this.lat), lng: parseFloat(this.lng)};
            this.zoom = 13;
            this.mapLoaded = true;
          });
        });
      });
    })

  }

  public getSessionId(): string {
    return this.app.getSessionId();
  }

  async sendEmail(): Promise<void>{
    const title=this.ad.title
    const adId=this.ad.id
    const text = this.renderer.selectRootElement('#mex');
    //TODO
    //Inserire nel form o chiamare oggetto ?
    //const emailUtente

    if(text.value){
      let result: boolean;
      console.log(this.owner.email);
      this.service.sendEmail(title,adId,text.value, this.owner.email, this.user.email).subscribe(a=> result = a).add(() => {
        if(result){
          alert("La Consegna del messaggio e' andata a buon fine il venditore ti rispondera' nel tempo' piu' breve possibile, tipicamente in 48h");
          this.onContact();
        }else{
          alert("Qualcosa e' andato storto, riprova tra qualche minuto");
        }
      });
    }
    else
      alert("Non puoi inviare una email vuota");

  }

  public addReview():void{
    const elem = document.getElementById('recens');
    const scriviUnaRec =document.getElementById("scriviUnaRece")

    if(!this.displayRece){
      elem?.classList.remove("nodisplay")
      elem?.classList.add("display")
      scriviUnaRec?.classList.add("nodisplay")
      this.displayRece=true;
    }
    else{
      elem?.classList.add("nodisplay")
      scriviUnaRec?.classList.remove("nodisplay")
      this.displayRece=false
    }
  }

  onContact(): void {
    const b=document.getElementById("contatta")
    const form= document.getElementById("display")
    const tel=document.getElementById("tel")
    if(!this.display){
      form?.classList.remove("nodisplay")
      form?.classList.add("display")
      b?.classList.add("nodisplay")
      tel?.classList.add("nodisplay")
      this.display=true;
    }
    else{
      form?.classList.add("nodisplay")
      b?.classList.remove("nodisplay")
      tel?.classList.remove("nodisplay")
      this.display=false
    }
  }

  sendReview():void{
    let canSend:Boolean=true

    const title=this.renderer.selectRootElement('#titleInput')
    let vote:any;
    vote=document.getElementById('rating')
    const description=this.renderer.selectRootElement('#description');
    if(!title.value || !description.value){
      canSend=false;
    }
    if(canSend){
      this.service.addReview(title.value,description.value,vote.value,this.user.nickname,this.ad.id).subscribe(result =>{
        if(result){
          alert("Recensione inserita con successo");
          this.changed=true;
        }
        else{
          alert("Qualcosa e' andato storto, riprova tra qualche minuto");
        }
      });
    }
  }

  isUserLogged(): boolean{
    return this.app.getLoggedIn();
  }

  onDelete(): void {
    let result: boolean;
    this.service.deleteAd(this.ad.id).subscribe(a => result = a).add(() =>{
      if(result == true)
        window.location.replace("/dashboard?sessionId=" + this.getSessionId());
      else{
        alert("Qualcosa e' andato storto, riprova tra qualche minuto");
      }
    });
  }

  onEditSave(): void{
    let title = this.renderer.selectRootElement('#titleValue');
    let price = this.renderer.selectRootElement('#priceValue');
    let description = this.renderer.selectRootElement('#descriptionValue');

    let result: boolean;
    this.service.editAd(this.ad.id, title.value, description.value, price.value).subscribe(a => {
      result = a;
      console.log(result);
      if(result === true)
        window.location.reload();
      else{
        alert("Qualcosa e' andato storto, riprova tra qualche minuto");
      }
    })
  }

}
