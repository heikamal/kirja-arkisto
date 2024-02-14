import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }
  book_url = '/api/kirjat'

  postData(data: JSON) {
    return this.http.post<JSON>(this.book_url, data);
  }
  getBook(){
    return this.http.get<JSON>(this.book_url);
  }
}
