export interface UserProfile {
  id: string;
  username: string;
  email: string;
  subscriptions: string[];
}

export interface UpdateProfileRequest {
  username: string;
  email: string;
  password: string;
}