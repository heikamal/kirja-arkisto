import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }
  book_url = 'http://localhost:8080/api/kirjat'

  postData(data: JSON) {
    return this.http.post<JSON>(this.book_url, data);
  }
  getBook(){
    return this.http.get<JSON>(this.book_url);
  }
}
