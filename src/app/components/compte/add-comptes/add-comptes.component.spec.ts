import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddComptesComponent } from './add-comptes.component';

describe('AddComptesComponent', () => {
  let component: AddComptesComponent;
  let fixture: ComponentFixture<AddComptesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddComptesComponent]
    });
    fixture = TestBed.createComponent(AddComptesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
