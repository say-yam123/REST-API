import { Component, OnInit } from '@angular/core';
import { AddressService } from '../address.service';
import { Address } from '../address.model';
import { FormsModule,FormBuilder,FormGroup,Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-address-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './address-list.component.html',
 // styleUrls: ['./address-list.component.css']
})
export class AddressListComponent implements OnInit {

  addresses: Address[] = [];

  addressForm: Address = {
    customerIdentifier: 0,
    customerAddressType: '',
    customerAddressValue: '',
    effectiveDate: ''
  };

  isEditing = false;

  constructor(private addressService: AddressService) {}

  ngOnInit(): void {
    this.loadAddresses();
  }

  loadAddresses(): void {
    this.addressService.getAddresses().subscribe(
      data => this.addresses = data,
      error => console.error(error)
    );
  }

  editAddress(address: Address): void {
    this.addressForm = {
      ...address,
      effectiveDate: address.effectiveDate?.split('T')[0]
    };
    this.isEditing = true;
  }

  saveAddress(): void {

    if (!this.addressForm.customerIdentifier || this.addressForm.customerIdentifier <= 0) {
      alert('Enter valid Customer ID');
      return;
    }

    const payload: Address = {
      ...this.addressForm,
      customerIdentifier: Number(this.addressForm.customerIdentifier)
    };

    console.log('Sending:', payload);

    if (this.isEditing) {

      this.addressService.updateAddress(payload).subscribe({
        next: () => {
          this.loadAddresses();
          this.resetForm();
        },
        error: err => console.error(err)
      });

    } else {

      const { addressId, ...data } = payload;

      this.addressService.addAddress(data as Address).subscribe({
        next: () => {
          this.loadAddresses();
          this.resetForm();
        },
        error: err => console.error(err)
      });
    }
  }

  deleteAddress(id: number | undefined): void {

    if (id == null) return;

    this.addressService.deleteAddress(id).subscribe(() => {
      this.loadAddresses();
    });
  }

  resetForm(): void {
    this.addressForm = {
      customerIdentifier: 0,
      customerAddressType: '',
      customerAddressValue: '',
      effectiveDate: ''
    };
    this.isEditing = false;
  }
}