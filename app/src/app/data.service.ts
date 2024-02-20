import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }
  book_url = 'http://localhost:8080/api/kirjat'
  series_url = 'http://localhost:8080/api/kirjasarjat'

  post_book(data: JSON) {
    return this.http.post<JSON>(this.book_url, data);
  }
  get_book(){
    return this.http.get<JSON>(this.book_url);
  }

  post_series(data: JSON) {
    return this.http.post<JSON>(this.series_url, data);
  }
  get_series(){
    return this.http.get<JSON>(this.series_url);
  }
}
