import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Topic } from 'src/app/core/models/topic.model';
import { PostService } from 'src/app/core/services/post.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss'],
})
export class CreatePostComponent implements OnInit {
  topics: Topic[] = [];
  loadingTopics = false;
  creating = false;
  errorMessage = '';

  form = this.fb.group({
    topicId: ['', Validators.required],
    title: ['', Validators.required],
    content: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private topicService: TopicService,
    private postService: PostService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loadingTopics = true;
    this.errorMessage = '';

    this.topicService.getTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.loadingTopics = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les thèmes';
        this.loadingTopics = false;
      },
    });
  }

  goBack(): void {
    this.location.back();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = {
      topicId: this.form.value.topicId ?? '',
      title: this.form.value.title ?? '',
      content: this.form.value.content ?? '',
    };

    this.creating = true;
    this.errorMessage = '';

    this.postService.createPost(payload).subscribe({
      next: (createdPost) => {
        this.creating = false;
        this.router.navigate(['/posts', createdPost.id]);
      },
      error: () => {
        this.creating = false;
        this.errorMessage = "Impossible de créer l'article";
      },
    });
  }
}