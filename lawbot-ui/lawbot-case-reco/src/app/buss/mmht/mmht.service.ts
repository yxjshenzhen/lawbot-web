import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BsModalService } from 'ngx-bootstrap/modal';
import { map } from 'rxjs/operators';

import { BaseService } from "../../common/service/base.service";
import { of } from 'rxjs/observable/of';
@Injectable()
export class MmhtService extends BaseService{

  constructor(private http: HttpClient ,private modalService: BsModalService) {
      super(http , modalService);
   }

  /**
   * 计算关键因子 OK
   * @param text 
   */
  calcFactors(text: string){
    return this.post("api/case-keys-mmht" ,{
      case_content: text
    });
  }

  handlerError(){}

  /**
   * 引用规则
   * @param keys 
   */
  getCaseRules(keys: Array<String>){
    return this.post("api/case-rules-mmht", {
      keys: keys
    })
  }

  /**
   * 同案推荐
   * @param keys 
   */
  getSameCases(keys: Array<String>){
    return this.post("api/case-same-mmht", {
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

    return this.get("api/case-laws-mmht/" + caseId).pipe(
      map((res: any) => {
        if(res.code == 200){
          this.caseLawsCached[caseId] = res;
        }
        return res;
      })
    );
  }

  //NO Changes
  getLaws(params){  
    return this.get("api/law/list" , {
      params: params
    })
  }
  
  getStats(params){
    return this.post("api/case-stats", params);
  }

}
