import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailListComponent } from './details-list.component';

describe('DetailsListComponent', () => {
  let component: DetailListComponent;
  let fixture: ComponentFixture<DetailListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
