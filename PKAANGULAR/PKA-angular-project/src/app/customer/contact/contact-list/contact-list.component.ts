import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from '../contact.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './contact-list.component.html',
  //styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent implements OnInit {

  contacts: Contact[] = [];

  contactForm: Contact = {
    customerIdentifier: 0,
    customerContactType: '',
    customerContactValue: '',
    effectiveDate: '',
    startDate: '',
    endDate: ''
  };

  isEditing = false;

  constructor(private contactService: ContactService) {}

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts(): void {
    this.contactService.getContacts().subscribe(
      data => this.contacts = data,
      error => console.error(error)
    );
  }

  editContact(contact: Contact): void {
    this.contactForm = {
      ...contact,
      effectiveDate: contact.effectiveDate?.split('T')[0],
      startDate: contact.startDate?.split('T')[0],
      endDate: contact.endDate?.split('T')[0]
    };
    this.isEditing = true;
  }

  saveContact(): void {

    if (!this.contactForm.customerIdentifier || this.contactForm.customerIdentifier <= 0) {
      alert('Enter valid Customer ID');
      return;
    }

    const payload: Contact = {
      ...this.contactForm,
      customerIdentifier: Number(this.contactForm.customerIdentifier)
    };

    console.log('Sending:', payload);

    if (this.isEditing) {

      this.contactService.updateContact(payload).subscribe({
        next: () => {
          this.loadContacts();
          this.resetForm();
        },
        error: err => console.error(err)
      });

    } else {

      const { contactInformationId, ...data } = payload;

      this.contactService.addContact(data as Contact).subscribe({
        next: () => {
          this.loadContacts();
          this.resetForm();
        },
        error: err => console.error(err)
      });
    }
  }

  deleteContact(id: number | undefined): void {

    if (id == null) return;

    this.contactService.deleteContact(id).subscribe(() => {
      this.loadContacts();
    });
  }

  resetForm(): void {
    this.contactForm = {
      customerIdentifier: 0,
      customerContactType: '',
      customerContactValue: '',
      effectiveDate: '',
      startDate: '',
      endDate: ''
    };
    this.isEditing = false;
  }
}
