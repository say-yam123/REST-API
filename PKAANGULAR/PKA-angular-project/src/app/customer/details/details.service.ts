import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Details } from './details.model';

@Injectable({
  providedIn: 'root'
})
export class DetailsService {

  private apiUrl = 'http://localhost:8080/api/customerDetails';

  constructor(private http: HttpClient) {}

  getDetails(): Observable<Details[]> {
    return this.http.get<Details[]>(this.apiUrl);
  }

  addDetails(details: Details): Observable<Details> {
    const { customerIdentifier, ...payload } = details;
    return this.http.post<Details>(this.apiUrl, payload);
  }

  updateDetails(details: Details): Observable<Details> {

    if (details.customerIdentifier == null) {
      throw new Error('Cannot update without id');
    }

    return this.http.put<Details>(
      `${this.apiUrl}/${details.customerIdentifier}`,
      details
    );
  }

  deleteDetails(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}