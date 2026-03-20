import { Component, OnInit } from '@angular/core';
import { TopicService } from 'src/app/core/services/topic.service';
import { Topic } from 'src/app/core/models/topic.model';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];
  loading = false;
  errorMessage = '';

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loading = true;
    this.errorMessage = '';

    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les thèmes';
        this.loading = false;
      },
    });
  }

  toggleSubscription(topic: Topic): void {
    const request$ = topic.subscribed
      ? this.topicService.unsubscribe(topic.id)
      : this.topicService.subscribe(topic.id);

    request$.subscribe({
      next: () => {
        topic.subscribed = !topic.subscribed;
      },
      error: () => {
        this.errorMessage = "Impossible de mettre à jour l'abonnement";
      },
    });
  }

  trackByTopicId(index: number, topic: Topic): string {
    return topic.id;
  }
}