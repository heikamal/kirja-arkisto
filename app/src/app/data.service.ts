import { HttpClient } from '@angular/common/http';
import { Component, Injectable } from '@angular/core';
import { Observable } from 'rxjs';


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
  remove_book(book_id: string): Observable<any> {
    const url = `${this.book_url}/${book_id}`;
    return this.http.delete<any>(url);
  }
  post_series(data: JSON) {
    return this.http.post<JSON>(this.series_url, data);
  }
  get_series(){
    return this.http.get<JSON>(this.series_url);
  }
  remove_series(series_id: string): Observable<any> {
    const url = `${this.series_url}/${series_id}`;
    return this.http.delete<any>(url);
  }
}
