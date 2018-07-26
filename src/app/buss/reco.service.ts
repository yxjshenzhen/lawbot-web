import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BsModalService } from 'ngx-bootstrap/modal';
import { map } from 'rxjs/operators';

import { BaseService } from "../common/service/base.service";
import { of } from 'rxjs/observable/of';
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


  /**
   * 获取案件引用法条
   * @param caseId 
   */
  caseLawsCached = {};
  getCaseLaws(caseId: string){
    if(this.caseLawsCached[caseId]){
      return of(this.caseLawsCached[caseId]);
    }

    return this.get("api/case-laws/" + caseId).pipe(
      map((res: any) => {
        if(res.code == 200){
          this.caseLawsCached[caseId] = res;
        }
        return res;
      })
    );
  }

  getLaws(params){  
    return this.get("api/law/list" , {
      params: params
    })
  }

  getStats(params){
    return this.post("api/case-stats", params);
  }

}
