import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPretComponent } from './list-pret.component';

describe('ListPretComponent', () => {
  let component: ListPretComponent;
  let fixture: ComponentFixture<ListPretComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListPretComponent]
    });
    fixture = TestBed.createComponent(ListPretComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
