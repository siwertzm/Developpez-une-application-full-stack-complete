import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostDetail } from 'src/app/core/models/post.model';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  post: PostDetail | null = null;
  loading = false;
  sending = false;
  errorMessage = '';

  commentForm = this.fb.group({
    content: ['', [Validators.required]],
  });

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPost();
  }

  loadPost(): void {
    const postId = this.route.snapshot.paramMap.get('id');

    if (!postId) {
      this.errorMessage = 'Article introuvable';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.postService.getPostById(postId).subscribe({
      next: (post) => {
        this.post = post;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = "Impossible de charger l'article";
        this.loading = false;
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/feed']);
  }

  submitComment(): void {
    if (this.commentForm.invalid || !this.post) {
      this.commentForm.markAllAsTouched();
      return;
    }

    const content = this.commentForm.value.content?.trim();

    if (!content) {
      return;
    }

    this.sending = true;
    this.errorMessage = '';

    this.postService.addComment(this.post.id, { content }).subscribe({
      next: (comment) => {
        if (this.post) {
          this.post.comments = [...this.post.comments, comment];
        }
        this.commentForm.reset();
        this.sending = false;
      },
      error: () => {
        this.errorMessage = "Impossible d'ajouter le commentaire";
        this.sending = false;
      },
    });
  }

  trackByCommentId(index: number, comment: { id: string }): string {
    return comment.id;
  }
}