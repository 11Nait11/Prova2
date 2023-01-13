import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ad } from './classes';
import { User } from './classes';
import { Property } from './classes';
import { Review } from './classes';
import { Auction } from './classes';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  constructor(private http: HttpClient) { }

  checkLoggedIn(sessionId: string): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/checkLoggedIn', {params: {sessionId: sessionId}});

  }

  makeAdmin(nickname: string) {
    return this.http.get<boolean>('http://localhost:8080/makeAdmin', {params: {nickname: nickname}});
  }

  getUserBySession(sessionId: string | null): Observable<User> {
    if(sessionId == null)
      return new Observable<User>();
    return this.http.get<User>('http://localhost:8080/user', {params: {parameter: sessionId, bySession: "true"}});
  }

  getUserByNickname(nickname: string): Observable<User> {
    return this.http.get<User>('http://localhost:8080/user', {params: {parameter: nickname, bySession: "false"}});
  }

  getAds(): Observable<Ad[]>{
    return this.http.get<Ad[]>('http://localhost:8080/ads');
  }

  searchAds(searchType: string, parameter: string): Observable<Ad[]>{
    return this.http.get<Ad[]>('http://localhost:8080/searchAds', {params: {searchType: searchType, parameter: parameter}});
  }

  addAd(sessionId: string, status: string, isAuction:string, city: string, title: string, description: string, type: string, mq: string, latitude: string, longitude: string, price: string, media: File): Observable<boolean> {

      return this.http.post<boolean>('http://localhost:8080/addAd', media, {params: {sessionId: sessionId, title: title, description: description, type: type, mq: mq, latitude: latitude, longitude: longitude, price: price, status: status, city: city, isAuction: isAuction.toString()}});

    /*
    var ad : Observable<string> = this.http.get<string>("http://localhost:8080" + "/addAd?" + "sessionId=" + sessionId + "&title=" + title + "&description=" + description + "&type=" + type + "&mq=" + mq + "&latitude=" + latitude + "&longitude=" + longitude);
    */
  }

  getImage(adId: number): Observable<Blob[]> {
    return this.http.get<Blob[]>('http://localhost:8080/getImage', {params: {adId: adId}, responseType: 'json'});
  }

  getAd(adId: string): Observable<Ad> {
    return this.http.get<Ad>('http://localhost:8080/ad?adId=' + adId);
  }

  sendEmail(title: string, adId: number, text: string, emailAnnuncio: string, emailMittente: string): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/sendEmail', {params: {title: title, adId: adId, text: text, emailAnnuncio: emailAnnuncio, emailMittente: emailMittente}});
  }

  addReview(title: string, description: string, rating: number, user: string, ad: number): Observable<boolean>{
    console.log(user);
    return this.http.get<boolean>('http://localhost:8080/newReview?title=' + title + '&description=' + description + '&rating=' + rating + '&user=' + user + '&ad=' + ad);
  }

  getReviews(adId: number): Observable<Review[]>{
    return this.http.get<Review[]>('http://localhost:8080/getReviews', {params: {adId: adId}});
  }

  getProperty(id: number): Observable<Property>{
    return this.http.get<Property>('http://localhost:8080/property?id=' + id);
  }

  removeReview(reviewId: number): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/removeReview?reviewId=' + reviewId);
  }

  banUser(nickname: string): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/banUser?nickname=' + nickname);
  }

  unbanUser(nickname: string): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/banUser?nickname=' + nickname);
  }

  updateUser(nickname: string, name:string, lastname:string, telephone: string, email:string, state:string, country:string, address: string, postalCode: string, password: string){
    return this.http.get<boolean>('http://localhost:8080/updateUser?nickname=' + nickname + '&name=' + name + '&lastname=' + lastname + '&telephone=' + telephone + '&email=' + email + '&state=' + state + '&country=' + country + '&address=' + address + '&postalCode=' + postalCode + '&password=' + password);
  }

  deleteAd(adId: number): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/deleteAd?adId=' + adId);
  }

  editAd(adId: number, title: string, description: string, price: string): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/editAd?adId=' + adId + '&title=' + title + '&description=' + description + '&price=' + price);
  }

  getAuction(id: number): Observable<Auction> {
    var asta:Observable<Auction>=this.http.get<Auction>("http://localhost:8080/getAuction?adId="+id)
    return asta
  }

  sendAuctionOffer(id?:number, offerta?:String,id_ad?:number):Observable<Boolean>{
    console.log("CHiamata arrivato "+id_ad)
    const offertaValida:Observable<Boolean>=this.http.get<Boolean>("http://localhost:8080/putOffer?id="+id+"&offerta="+offerta+"&id_ad="+id_ad)
    return offertaValida
  }


}
