import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Topic } from 'src/app/core/models/topic.model';
import { UpdateProfileRequest, UserProfile } from 'src/app/core/models/user.model';
import { TopicService } from 'src/app/core/services/topic.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  loading = false;
  saving = false;
  errorMessage = '';
  successMessage = '';

  profile: UserProfile | null = null;
  subscribedTopics: Topic[] = [];

  form = this.fb.group({
    username: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: [''],
  });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private topicService: TopicService
  ) {}

  ngOnInit(): void {
    this.loadProfilePage();
  }

  loadProfilePage(): void {
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    forkJoin({
      me: this.userService.getMe(),
      topics: this.topicService.getTopics(),
    }).subscribe({
      next: ({ me, topics }) => {
        this.profile = me;

        this.form.patchValue({
          username: me.username,
          email: me.email,
          password: '',
        });

        this.subscribedTopics = topics.filter((topic) =>
          me.subscriptions.includes(topic.name)
        );

        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger le profil';
        this.loading = false;
      },
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload: UpdateProfileRequest = {
      username: this.form.value.username ?? '',
      email: this.form.value.email ?? '',
      password: this.form.value.password ?? '',
    };

    this.saving = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.userService.updateMe(payload).subscribe({
      next: (updatedUser) => {
        this.profile = updatedUser;

        this.form.patchValue({
          username: updatedUser.username,
          email: updatedUser.email,
          password: '',
        });

        this.successMessage = 'Profil mis à jour';
        this.saving = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de mettre à jour le profil';
        this.saving = false;
      },
    });
  }

  unsubscribe(topic: Topic): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.topicService.unsubscribe(topic.id).subscribe({
      next: () => {
        this.subscribedTopics = this.subscribedTopics.filter(
          (t) => t.id !== topic.id
        );
        this.successMessage = 'Désabonnement effectué';
      },
      error: () => {
        this.errorMessage = 'Impossible de se désabonner';
      },
    });
  }

  trackByTopicId(index: number, topic: Topic): string {
    return topic.id;
  }
}