import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Classification } from './classification.model';

@Injectable({
  providedIn: 'root'
})
export class ClassificationService {

  private apiUrl = 'http://localhost:8080/api/customerClassificationTypes';

  constructor(private http: HttpClient) {}

  getClassifications(): Observable<Classification[]> {
    return this.http.get<Classification[]>(this.apiUrl);
  }

  addClassification(classification: Classification): Observable<Classification> {
    const { customerClassificationId, ...payload } = classification;
    return this.http.post<Classification>(this.apiUrl, payload);
  }

  updateClassification(classification: Classification): Observable<Classification> {

    if (classification.customerClassificationId == null) {
      throw new Error('Cannot update classification without id');
    }

    return this.http.put<Classification>(
      `${this.apiUrl}/${classification.customerClassificationId}`,
      classification
    );
  }

  deleteClassification(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}