import { TestBed, inject } from '@angular/core/testing';

import { MjjdService } from './mjjd.service';

describe('MjjdService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MjjdService]
    });
  });

  it('should be created', inject([MjjdService], (service: MjjdService) => {
    expect(service).toBeTruthy();
  }));
});
