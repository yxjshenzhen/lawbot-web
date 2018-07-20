import { Injectable } from '@angular/core';
import { BaseService } from '../common/service/base.service';
import { HttpClient } from '@angular/common/http';
import { BsModalService } from 'ngx-bootstrap/modal';
@Injectable()
export class ContractService extends BaseService{

  constructor(private modalService: BsModalService ,private http: HttpClient) {
    super(http , modalService);
  }

  
  compare(formData: FormData){
    return this.post("api/compare" , formData);
  }

}
