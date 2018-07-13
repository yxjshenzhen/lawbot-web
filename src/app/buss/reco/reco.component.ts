import { Component, OnInit } from '@angular/core';

import { RecoService } from "../reco.service";

@Component({
  selector: 'app-reco',
  templateUrl: './reco.component.html',
  styleUrls: ['./reco.component.css']
})
export class RecoComponent implements OnInit {

  constructor(private recoService: RecoService) { }

  caseText: string = "2008年9月10日，被告贺麒麟因做生意需要资金向原告王燕芬借款210万元，双方约定利息按月利率4.5%计算。2009年8月30日，被告贺伟峰为借款作了担保，约定2013年后还款。同日，被告出具借条。后被告贺麒麟未按照约定支付利息，原告多次催讨无果。现原告诉至法院要求：被告支付之前拖欠的利息，被告支付逾期利息1万元，被告承担律师代理费，被告承担诉讼费。";
  showFactors: boolean = false;
  caseKeysLoading: boolean = false;
  caseLoading = 0;
  caseTabIndex = 0;

  factors: Array<string> = [];

  rules: Array<Object> = [];

  sameCases: any = {
    cases_leve1: [],
    cases_leve2: [],
    cases_leve3: [],
    cases_leve4: []
  };

  activeCase:any = [];

  ngOnInit() {
  }

  caseTabClick(e , i){
    e.preventDefault(); 
    this.caseTabIndex = i;
    if(i > 0)
      this.activeCase = this.sameCases['cases_leve' + i];
  }
  analyze(){
    this.caseKeysLoading = true;
    this.factors = [];
    this.recoService.calcFactors(this.caseText).subscribe((res: any) => {
      let data = res.data, code = res.code;
      if(code == 200){
        this.factors = data.caseKeys;  
        this.loadCaseRules();
      }else{
        alert(res.message);
      }
      this.showFactors = true;
      this.caseKeysLoading = false;  
    })
  }

  loadCaseRules(){
    this.caseLoading = 2;
    if(this.factors.length == 0) return;
    this.recoService.getCaseRules(this.factors).subscribe((res: any) => {
      let data = res.data , code = res.code;
      if(code == 200){
        this.rules = data.caseRules.rules;
      }else{
        alert(res.message);
      }
      this.caseLoading --;
    });
    this.recoService.getSameCases(this.factors).subscribe((res: any) => {
      let data = res.data , code = res.code;
      if(code == 200){
        this.sameCases = data.sameCases;
      }else{
        alert(res.message);
      }
      this.caseLoading --;
    })
  }

}
