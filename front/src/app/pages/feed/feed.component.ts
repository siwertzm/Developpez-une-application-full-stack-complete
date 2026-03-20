import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/core/services/post.service';
import { Post } from 'src/app/core/models/post.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  loading = false;
  errorMessage = '';
  sort: 'asc' | 'desc' = 'desc';
  mobileMenuOpen = false;

  constructor(private postService: PostService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.loading = true;
    this.errorMessage = '';

    this.postService.getFeed(this.sort).subscribe({
      next: (posts) => {
        this.posts = posts;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Impossible de charger les articles';
        this.loading = false;
      },
    });
  }

  toggleSort(): void {
    this.sort = this.sort === 'desc' ? 'asc' : 'desc';
    this.loadPosts();
  }

  toggleMobileMenu(): void {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

  closeMobileMenu(): void {
    this.mobileMenuOpen = false;
  }

  trackByPostId(index: number, post: Post): string {
    return post.id;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}