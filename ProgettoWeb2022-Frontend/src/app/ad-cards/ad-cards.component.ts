import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Ad} from '../classes';
import { User } from '../classes';
import { Property } from '../classes';
import { ServerService } from '../server.service';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-ad-cards',
  templateUrl: './ad-cards.component.html',
  styleUrls: ['./ad-cards.component.css']
})

export class AdCardsComponent implements OnChanges {

  @Input() ads: Ad[] = [] //Array di annunci
  @Input() filter: string = ""; //Filtro di annunci
  @Input() order: number = 0; //Ordinamento di annunci

  constructor(private app: AppComponent, private service: ServerService, private sanitazer: DomSanitizer) {

  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("ngOnChanges: " + changes);
    console.log(this.ads.length);

    if(this.order == 1){
      this.ads.sort((a,b) => a.price - b.price);
    }else if(this.order == 2){
      this.ads.sort((a,b) => b.price - a.price);
    }else{
      this.ads = this.ads;
    }

    for(let i = 0; i< this.ads.length; i++){
      this.service.getImage(this.ads[i].id).subscribe(blobList => this.ads[i].images = blobList).add( () => {
        let image = document.getElementById("img" + this.ads[i].id) as HTMLImageElement;
        if(this.ads[i].images.at(0) == null){
          image.src = "https://fakeimg.pl/400x250/?text=No%20image"
        }else{
          image.src = 'data:image/jpeg;base64,' + this.ads[i].images.at(0);
        }
      });
    }
  }

  public ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
  }

  public ngAfterContentChecked(): void {
    //Called after every check of the component's or directive's content.
    //Add 'implements AfterContentChecked' to the class.
  }

  public getSessionId():string{
    return this.app.getSessionId();
  }

  public isEmpty():boolean{
    return this.ads.length == 0;
  }

  public canShow(status: string):boolean{
    if(this.filter == "all"){
      return true;
    }else if(this.filter == "rent" && status == "affittasi"){
      return true;
    }else if(this.filter == "sell" && status == "vendesi"){
      return true;
    }else{
      return false;
    }
  }

}
