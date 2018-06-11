import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegleComponent } from './regle.component';

describe('RegleComponent', () => {
  let component: RegleComponent;
  let fixture: ComponentFixture<RegleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
