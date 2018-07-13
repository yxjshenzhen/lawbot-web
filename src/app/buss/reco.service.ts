import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RecoService {

  constructor(private http: HttpClient) { }

  /**
   * 计算关键因子
   * @param text 
   */
  calcFactors(text: string){
    return this.http.post("api/case-keys" ,{
      case_content: text
    });
  }

  /**
   * 引用规则
   * @param keys 
   */
  getCaseRules(keys: Array<String>){
    return this.http.post("api/case-rules", {
      keys: keys
    })
  }

  /**
   * 同案推荐
   * @param keys 
   */
  getSameCases(keys: Array<String>){
    return this.http.post("api/case-same", {
      keys: keys
    })
  }

}
