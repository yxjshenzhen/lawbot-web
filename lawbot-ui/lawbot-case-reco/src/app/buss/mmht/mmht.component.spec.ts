import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MmhtComponent } from './mmht.component';

describe('MmhtComponent', () => {
  let component: MmhtComponent;
  let fixture: ComponentFixture<MmhtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MmhtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MmhtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
