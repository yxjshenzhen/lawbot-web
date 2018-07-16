import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BsModalService } from 'ngx-bootstrap/modal';

import { BaseService } from "./base.service";

@Injectable()
export class UserService extends BaseService{

  constructor(private http: HttpClient,private modalService: BsModalService) { 
    super(http ,modalService);
  }

  currentUser(){
    return this.get("/account/me");
  }

}
