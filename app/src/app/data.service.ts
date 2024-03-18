import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from './book';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient, private cookieService: CookieService) { }
  book_url = 'http://localhost:8080/api/kirjat';
  series_url = 'http://localhost:8080/api/kirjasarjat';
  registeration_url = 'http://localhost:8080/api/auth/signup';
  login_url = 'http://localhost:8080/api/auth/signin';  
  bookshelf_url = 'http://localhost:8080/api/kirjahyllyt/self';  
  bookcopy_url = 'http://localhost:8080/api/kirjakopiot';  
  photo_url = 'http://localhost:8080/api/kirjat/kuvat';
  ownership_url = 'http://localhost:8080/api/kirjat'

  post_book(data: JSON): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.post<JSON>(this.book_url, data, { headers });
  }

  post_book_copy(data: JSON): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.post<JSON>(this.bookcopy_url, data, { headers });
  }

  post_photo(data: JSON): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.post<JSON>(this.photo_url, data, { headers });
  }


  get_books(): Observable<Book[]>{
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.get<Book[]>(this.book_url, { headers });
  }

  get_book(book_id: string): Observable<any> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    const url = `${this.book_url}/${book_id}`;
    return this.http.get<JSON>(url, { headers });
  }
  
  get_photo(data: JSON): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.post<JSON>(this.photo_url, data, { headers });
  }
  

  get_bookshelf(): Observable<any> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.get<JSON>(this.bookshelf_url, { headers });
  }

  remove_book(book_id: string): Observable<any> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    const url = `${this.book_url}/${book_id}`;
    return this.http.delete<any>(url, { headers });
  }

  post_series(data: JSON): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.post<JSON>(this.series_url, data, { headers });
  }

  get_series(): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    return this.http.get<JSON>(this.series_url, { headers });
  }

  get_series_info(series_id: string): Observable<any> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    const url = `${this.series_url}/${series_id}`;
    return this.http.get<JSON>(url, { headers });
  }

  remove_series(series_id: string): Observable<any> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    const url = `${this.series_url}/${series_id}`;
    return this.http.delete<any>(url, { headers });
  }

  register_user(data: JSON): Observable<JSON> {
    return this.http.post<JSON>(this.registeration_url, data);
  }

  login_user(data: JSON): Observable<JSON> {
    return this.http.post<JSON>(this.login_url, data);
  }
  get_ownership(book_id: number): Observable<JSON> {
    const accessToken = this.cookieService.get('accessToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${accessToken}`);
    const url = `${this.ownership_url}/${book_id}/owned`;
    return this.http.get<JSON>(url, { headers });
  }
}
