import { Component, OnInit } from '@angular/core';
import { IdentityService } from '../identity.service';
import { Identity } from '../identity.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-identity-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './identity-list.component.html',
  //styleUrls: ['./identity-list.component.css']
})
export class IdentityListComponent implements OnInit {

  identities: Identity[] = [];

  identityForm: Identity = {
    proofOfId: 0,
    customerIdentifier: 0,
    proofOfIdType: '',
    proofOfIdValue: '',
    startDate: '',
    endDate: '',
    effectiveDate: ''
  };

  isEditing = false;

  constructor(private identityService: IdentityService) {}

  ngOnInit(): void {
    this.loadIdentities();
  }

  loadIdentities(): void {
    this.identityService.getIdentities().subscribe(
      data => this.identities = data,
      error => console.error('Error fetching identities:', error)
    );
  }

  editIdentity(identity: Identity): void {
    this.identityForm = {
      ...identity,
      startDate: identity.startDate?.split('T')[0],
      endDate: identity.endDate?.split('T')[0],
      effectiveDate: identity.effectiveDate?.split('T')[0]
    };
    this.isEditing = true;
  }

  saveIdentity(): void {
    console.log('CLICKED');
  console.log(this.identityForm);
    const payload: Identity = {
      ...this.identityForm,
      customerIdentifier: Number(this.identityForm.customerIdentifier)
    };

    console.log('Sending:', payload);

    if (this.isEditing) {

      this.identityService.updateIdentity(payload).subscribe({
        next: () => {
          this.loadIdentities();
          this.resetForm();
        },
        error: err => console.error(err)
      });

    } else {

      const { proofOfId, ...data } = payload;

      this.identityService.addIdentity(data as Identity).subscribe({
        next: () => {
          this.loadIdentities();
          this.resetForm();
        },
        error: err => console.error(err)
      });
    }
  }

  deleteIdentity(id: number | undefined): void {

    if (id == null) return;

    this.identityService.deleteIdentity(id).subscribe(() => {
      this.loadIdentities();
    });
  }

  resetForm(): void {
    this.identityForm = {
      customerIdentifier: 0,
      proofOfIdType: '',
      proofOfIdValue: '',
      startDate: '',
      endDate: '',
      effectiveDate: ''
    };
    this.isEditing = false;
  }
}