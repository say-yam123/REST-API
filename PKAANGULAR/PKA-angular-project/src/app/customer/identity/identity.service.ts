import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Identity } from './identity.model';

@Injectable({
  providedIn: 'root'
})
export class IdentityService {

  private apiUrl = 'http://localhost:8080/api/customerProofOfId';

  constructor(private http: HttpClient) {}

  getIdentities(): Observable<Identity[]> {
    return this.http.get<Identity[]>(this.apiUrl);
  }

  addIdentity(identity: Identity): Observable<Identity> {
    const { proofOfId, ...payload } = identity;
    return this.http.post<Identity>(this.apiUrl, payload);
  }

  updateIdentity(identity: Identity): Observable<Identity> {

    if (identity.proofOfId == null) {
      throw new Error('Cannot update without id');
    }

    return this.http.put<Identity>(
      `${this.apiUrl}/${identity.proofOfId}`,
      identity
    );
  }

  deleteIdentity(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}