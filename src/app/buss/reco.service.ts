import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BsModalService } from 'ngx-bootstrap/modal';

import { BaseService } from "../common/service/base.service";
@Injectable()
export class RecoService extends BaseService{

  constructor(private http: HttpClient ,private modalService: BsModalService) {
      super(http , modalService);
   }

  /**
   * 计算关键因子
   * @param text 
   */
  calcFactors(text: string){
    return this.post("api/case-keys" ,{
      case_content: text
    });
  }

  handlerError(){}

  /**
   * 引用规则
   * @param keys 
   */
  getCaseRules(keys: Array<String>){
    return this.post("api/case-rules", {
      keys: keys
    })
  }

  /**
   * 同案推荐
   * @param keys 
   */
  getSameCases(keys: Array<String>){
    return this.post("api/case-same", {
      keys: keys
    })
  }

  getCaseDetail(caseId: string){
    return this.get("api/case-detail/" + caseId);
  }


  getLaws(params){  
    return this.get("api/law/list" , {
      params: params
    })
  }

}
