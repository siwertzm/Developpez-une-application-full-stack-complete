export interface Post {
  id: string;
  title: string;
  topicName: string;
  authorUsername: string;
  createdAt: string;
  content: string;
}
export interface Comment {
  id: string;
  content: string;
  authorUsername: string;
  createdAt: string;
}

export interface PostDetail {
  id: string;
  title: string;
  content: string;
  topicName: string;
  authorUsername: string;
  createdAt: string;
  comments: Comment[];
}

export interface CreateCommentRequest {
  content: string;
}

export interface CreatePostRequest {
  topicId: string;
  title: string;
  content: string;
}