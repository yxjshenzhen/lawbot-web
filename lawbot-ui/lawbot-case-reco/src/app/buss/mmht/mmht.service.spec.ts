import { TestBed, inject } from '@angular/core/testing';

import { MmhtService } from './mmht.service';

describe('MmhtService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MmhtService]
    });
  });

  it('should be created', inject([MmhtService], (service: MmhtService) => {
    expect(service).toBeTruthy();
  }));
});
