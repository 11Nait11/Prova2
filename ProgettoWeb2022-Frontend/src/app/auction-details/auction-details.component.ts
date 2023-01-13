import { Component } from '@angular/core';
import { ServerService } from '../server.service';
import { Auction } from '../classes';
import { Input } from '@angular/core';

@Component({
  selector: 'app-auction-details',
  templateUrl: './auction-details.component.html',
  styleUrls: ['./auction-details.component.css']
})
export class AuctionDetailsComponent {

  isEnd:Boolean=false;
  isAccept:Boolean=false;
  asta?: Auction;
  offerAmount?:string;
  @Input() adId: number = 0;

  constructor(private service:ServerService){

  }

  ngOnInit(): void {

    setInterval(this.checkOfferta, 1000);
    this.service.getAuction(this.adId).subscribe(a=>this.asta=a)

  }


  checkOfferta(){

    const offerAmountInput = document.getElementById("offer-amount") as HTMLInputElement;
    this.offerAmount = offerAmountInput.value;

    //unico modo non riesco a prende valore da asta
    const currentPrice= document.getElementById("currentPrice") as HTMLParagraphElement



    const modal = document.getElementById("make-offer-modal") as HTMLInputElement;
    const button = document.getElementById("chiudi") as HTMLButtonElement;
    const infoPrezzo= document.getElementById("infoPrezzo") as HTMLParagraphElement



    if(this.offerAmount){
      if(parseInt(this.offerAmount)> parseInt(currentPrice.textContent!))
          button.disabled=false
      else{
        button.disabled=true
        infoPrezzo.style.display="block"
      }

    }
    else
      button.disabled=true



  }


  async inviaOfferta(){
    const offerAmountInput = document.getElementById("offer-amount") as HTMLInputElement;
    this.offerAmount = offerAmountInput.value;
    console.log("invio asta iddd  "+this.adId);
    console.log("invio asta id"+this.asta?.id);
    await this.service.sendAuctionOffer(this.asta?.id,this.offerAmount,this.adId).subscribe(a=>this.isAccept=a)

    setTimeout(() => {
    if(this.isAccept){
      alert("Offerta accettata! ")
      if(this.asta)
        this.asta.currentPrice=Number(this.offerAmount);
    }}, 100);
  }





  updateTimeRemaining() {
    let timeRemaining = this.getTimeRemaining();
    document.getElementById("timeReal")!.innerHTML = timeRemaining;
  }

  getTimeRemaining(): string {
    let end=this.asta!.endTime
    let now = new Date();
    let auctionEnd = new Date(end);
    let timeRemaining = auctionEnd.getTime() - now.getTime();

    if (timeRemaining < 0) {
      this.isEnd=true
      return "L'asta è già terminata";
    }

    let days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));
    let hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    let minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
    let seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);


    return `Tempo rimanente: ${days} giorni, ${hours} ore, ${minutes} minuti, ${seconds} secondi`;
  }


}
