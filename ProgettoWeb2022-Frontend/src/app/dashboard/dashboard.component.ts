import { Component, Input, OnInit } from '@angular/core';
import { Ad } from '../classes';
import { User } from '../classes';
import { Property } from '../classes';
import { ServerService } from '../server.service';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  //Array di annunci
  public ads: Ad[] = [];
  public sessionId: string = "";
  public searchType: string = "Città";
  public adsFilter: string = "all";
  public order: number = 0;

  constructor(private service: ServerService, private app: AppComponent) { }

  ngOnInit(): void {
    //Ci serve il service che prenda tutti gli annunci dal database
    //someService.getAds().subscribe(ads => this.ads = ads);
    this.service.getAds().subscribe(ads => this.ads = ads);
  }

  public search(){
      console.log(this.searchType);
      let parameter = (<HTMLInputElement>document.getElementById("searchInput")).value;
      if(parameter != "" || parameter != null){
        this.ads = [];
        this.service.searchAds(this.searchType, parameter).subscribe(ads => this.ads = ads);
      }
  }

  public getSessionId():string{
    return this.app.getSessionId();
  }

  public onChange(event:Event){
    this.searchType = (<HTMLInputElement>event.target).value;
    console.log("Search type: " + this.searchType);
  }

  public onAdsFilterChange(parameter:string){
    this.adsFilter = parameter;
    console.log("Ads filter: " + this.adsFilter);
  }

  public getSearchType():string{
    return this.searchType;
  }

  public onOrderChange(){
    if(this.order + 1 == 3){
      this.order = 0;
      return;
    }
    this.order = this.order + 1;
  }

}
