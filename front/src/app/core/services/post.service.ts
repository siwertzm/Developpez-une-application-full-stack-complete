import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/posts`;

  constructor(private http: HttpClient) {}

  getFeed(sort: 'asc' | 'desc' = 'desc'): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}?sort=${sort}`);
  }
}