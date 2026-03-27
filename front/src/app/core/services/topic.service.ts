import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from '../models/topic.model';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private apiUrl = `${environment.apiUrl}/topics`;

  constructor(private http: HttpClient) {}

  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  subscribe(topicId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${topicId}/subscribe`, {});
  }

  unsubscribe(topicId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${topicId}/subscribe`);
  }
}