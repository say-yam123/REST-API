import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users'; // Replace with your Spring Boot API URL

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

 /*  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  } */
  addUser(user: User): Observable<User> {
  const { id, ...payload } = user;   // drop id if present
  return this.http.post<User>(this.apiUrl, payload);
}

  updateUser(user: User): Observable<User> {
  if (user.id == null) {
    throw new Error('Cannot update user without id');
  }
  return this.http.put<User>(`${this.apiUrl}/${user.id}`, user);
}

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
