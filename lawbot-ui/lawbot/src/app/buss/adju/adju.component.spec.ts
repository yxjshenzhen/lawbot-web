import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdjuComponent } from './adju.component';

describe('AdjuComponent', () => {
  let component: AdjuComponent;
  let fixture: ComponentFixture<AdjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
