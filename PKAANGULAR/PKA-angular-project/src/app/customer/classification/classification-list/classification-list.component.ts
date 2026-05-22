import { Component, OnInit } from '@angular/core';
import { ClassificationService } from '../classification.service';
import { Classification } from '../classification.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-classification-list',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './classification-list.component.html',
  //styleUrls: ['./classification-list.component.css']
})
export class ClassificationListComponent implements OnInit {

  classifications: Classification[] = [];

  classificationForm: Classification = {
    customerClassificationId: 0,
    customerClassificationType: '',
    customerClassificationValue: '',
    effectiveDate: ''
  };

  isEditing = false;

  constructor(private classificationService: ClassificationService) {}

  ngOnInit(): void {
    this.loadClassifications();
  }

  loadClassifications(): void {
    this.classificationService.getClassifications().subscribe(
      data => this.classifications = data,
      error => console.error('Error fetching classifications:', error)
    );
  }

  editClassification(classification: Classification): void {
    this.classificationForm = { ...classification };
    this.isEditing = true;
  }

  saveClassification(): void {

    if (this.isEditing) {

      if (this.classificationForm.customerClassificationId == null) return;

      this.classificationService.updateClassification(this.classificationForm)
        .subscribe(() => {
          this.loadClassifications();
          this.resetForm();
        });

    } else {

      const { customerClassificationId, ...payload } = this.classificationForm;

      this.classificationService.addClassification(payload as Classification)
        .subscribe(() => {
          this.loadClassifications();
          this.resetForm();
        });

    }
  }

  deleteClassification(id: number | undefined): void {

    if (id == null) return;

    this.classificationService.deleteClassification(id)
      .subscribe(() => {
        this.loadClassifications();
      });
  }

  resetForm(): void {
    this.classificationForm = {
      customerClassificationType: '',
      customerClassificationValue: '',
      effectiveDate: ''
    };
    this.isEditing = false;
  }
}