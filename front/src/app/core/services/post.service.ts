import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CreateCommentRequest, Post, PostDetail, Comment } from '../models/post.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/posts`;

  constructor(private http: HttpClient) {}

  getFeed(sort: 'asc' | 'desc' = 'desc'): Observable<PostDetail[]> {
    return this.http.get<PostDetail[]>(`${this.apiUrl}?sort=${sort}`);
  }

  getPostById(postId: string): Observable<PostDetail> {
    return this.http.get<PostDetail>(`${this.apiUrl}/${postId}`);
  }

  addComment(
    postId: string,
    payload: CreateCommentRequest,
  ): Observable<Comment> {
    return this.http.post<Comment>(
      `${this.apiUrl}/${postId}/comments`,
      payload,
    );
  }
}
