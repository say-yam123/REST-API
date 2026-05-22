import { Component, OnInit } from '@angular/core';
import { DetailsService } from '../details.service';
import { Details } from '../details.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-detail-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './details-list.component.html',
  //styleUrls: ['./details-list.component.css']
})
export class DetailListComponent implements OnInit {

  details: Details[] = [];

  detailForm: Details = {
    customerIdentifier: 0,
    customerCountryOfOrigin: '',
    customerDateOfBirth: '',
    customerFullName: '',
    customerGender: '',
    customerPreferredLanguage: '',
    customerStatus: '',
    customerType: 0
  };

  isEditing = false;

  constructor(private detailsService: DetailsService) {}

  ngOnInit(): void {
    this.loadDetails();
  }

  loadDetails(): void {
    this.detailsService.getDetails().subscribe(
      data => this.details = data,
      error => console.error('Error fetching details:', error)
    );
  }

  editDetails(detail: Details): void {
    this.detailForm = { ...detail };
    this.isEditing = true;
  }

  saveDetails(): void {

    if (this.isEditing) {

      if (this.detailForm.customerIdentifier == null) return;

      this.detailsService.updateDetails(this.detailForm)
        .subscribe(() => {
          this.loadDetails();
          this.resetForm();
        });

    } else {

      const { customerIdentifier, ...payload } = this.detailForm;

      this.detailsService.addDetails(payload as Details)
        .subscribe(() => {
          this.loadDetails();
          this.resetForm();
        });

    }
  }

  deleteDetails(id: number | undefined): void {

    if (id == null) return;

    this.detailsService.deleteDetails(id)
      .subscribe(() => {
        this.loadDetails();
      });
  }

  resetForm(): void {
    this.detailForm = {
      customerCountryOfOrigin: '',
      customerDateOfBirth: '',
      customerFullName: '',
      customerGender: '',
      customerPreferredLanguage: '',
      customerStatus: '',
      customerType: 0
    };
    this.isEditing = false;
  }
}