import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address } from './address.model';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private apiUrl = 'http://localhost:8080/api/customerAddress';

  constructor(private http: HttpClient) {}

  getAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.apiUrl);
  }

  addAddress(address: Address): Observable<Address> {
    const { addressId, ...payload } = address;
    return this.http.post<Address>(this.apiUrl, payload);
  }

  updateAddress(address: Address): Observable<Address> {

    if (address.addressId == null) {
      throw new Error('Cannot update without ID');
    }

    return this.http.put<Address>(
      `${this.apiUrl}/${address.addressId}`,
      address
    );
  }

  deleteAddress(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}