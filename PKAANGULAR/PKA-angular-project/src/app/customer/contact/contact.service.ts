import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contact } from './contact.model';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private apiUrl = 'http://localhost:8080/api/customerContact';

  constructor(private http: HttpClient) {}

  getContacts(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.apiUrl);
  }

  addContact(contact: Contact): Observable<Contact> {
    const { contactInformationId, ...payload } = contact;
    return this.http.post<Contact>(this.apiUrl, payload);
  }

  updateContact(contact: Contact): Observable<Contact> {

    if (contact.contactInformationId == null) {
      throw new Error('Cannot update without ID');
    }

    return this.http.put<Contact>(
      `${this.apiUrl}/${contact.contactInformationId}`,
      contact
    );
  }

  deleteContact(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
} 