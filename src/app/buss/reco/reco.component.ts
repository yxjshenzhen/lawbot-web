import { Component, OnInit,TemplateRef  } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { RecoService } from "../reco.service";

@Component({
  selector: 'app-reco',
  templateUrl: './reco.component.html',
  styleUrls: ['./reco.component.css']
})
export class RecoComponent implements OnInit {

  constructor(private recoService: RecoService,private modalService: BsModalService) { }

  modalRef: BsModalRef;

  eleStates: any ={
    ruleCollapse: false
  }

  modalData: any = {
    caseContent: ''
  }

  caseText: string = "2008年9月10日，被告贺麒麟因做生意需要资金向原告王燕芬借款210万元，双方约定利息按月利率4.5%计算。";
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

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
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
        this.rules = data.caseRules.rules.map(rule=>{
          if(rule.law){
            let laws = rule.law.split('\r\n');
            rule.law = laws.map(law => {
              return {
                title: law.substr(0 , law.indexOf(":")),
                content: law
              }
            });
          }
          
          return rule;
        });
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
