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
    caseObj: {},
    index: 1
  }
  lawModal: any = {

  }

  caseText: string = "2008年9月10日，被告贺麒麟因做生意需要资金向原告王燕芬借款210万元，双方约定利息按月利率4.5%计算。2009年8月30日，被告贺伟峰为借款作了担保，约定2013年后还款。同日，被告出具借条。后被告贺麒麟未按照约定支付利息，原告多次催讨无果。现原告诉至法院要求：被告支付之前拖欠的利息，被告支付逾期利息1万元，被告承担律师代理费，被告承担诉讼费。";
  showFactors: boolean = false;
  caseKeysLoading: boolean = false;
  caseLoading = 0;
  caseTabIndex = 0;

  factors: Array<string> = [];

  rules: Array<Object> = [];
  exportRules: Array<Object> = [];

  sameCases: any = {
    cases_leve1: [],
    cases_leve2: [],
    cases_leve3: [],
    cases_leve4: []
  };

  checkedCourt: Array<boolean> = [true,true,true,true];

  laws:any = {};

  activeCase:any = [];

  geoOptions: any;
  geoUpdateOptions:any;
  geoData = [];

  pieOptions: any; 
  pieUpdateOptions: any;
  pieLegendData: any;
  pieData: any;
  ngOnInit() {
    this.geoOptions = {
      title : {
          text: '推荐案件地区分布',
          subtext: '                      ——小法博'
      },
      tooltip : {
          trigger: 'item'
      },
      dataRange: {
          orient: 'horizontal',
          min: 0,
          max: 550,
          text:['高','低'],           // 文本，默认为数值文本
          splitNumber:0
      },								   
      series : [
          {
              name: '推荐案件地区分布',
              type: 'map',
              mapType: 'china',
              mapLocation: {
                  x: 'left'
              },
              selectedMode : 'multiple',
              itemStyle:{
                  normal:{label:{show:true}},
                  emphasis:{label:{show:true}}
              },
              //共31个省市
              data: this.geoData
          }
      ],								    
      animation: false
    };
    this.pieOptions = {
      title : {
          text: '各级法院案件占比',
          subtext: '                   ------小法博',
          x:'center'
      },
      tooltip : {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
      },
      legend: {
          orient: 'vertical',
          left: 'left',
          data: this.pieLegendData
      },
      series : [
          {
              name: '访问来源',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data: this.pieData,
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
          }
      ]
    };
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template , {
      'class': 'modal-lg'
    });
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
        if(this.factors.length > 0){
          this.loadStats();
          this.loadLaws();
          this.loadCaseRules();
        }
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
        this.rules = data.caseRules;
      }
      this.caseLoading --;
    });
    this.recoService.getSameCases(this.factors).subscribe((res: any) => {
      let data = res.data , code = res.code;
      if(code == 200){
        this.sameCases = data.sameCases;
      }
      this.caseLoading --;
    })
  }
  loadStats(){
    let params = {
      caseKeys: this.factors,
      courtLevels: this.checkedCourt.map((c ,i) => c ? i + 1 : 0).filter(c => c > 0)
    };
    if(params.courtLevels.length <= 0) return;
    this.recoService.getStats(params).subscribe((res: any) => {
      if(res.code == 200){
        //update geo chart
        this.geoData = res.data.stats.cities;
        this.geoUpdateOptions = {
          series: [{
            data: this.geoData
          }]
        }

        //update pie chart
        this.pieData = res.data.stats.levels;
        this.pieLegendData = this.pieData.map( (l: any) => {
          return l.name;
        });
        this.pieUpdateOptions = {
          legend: {
            data: this.pieLegendData
          },
          series: [{
            data: this.pieData 
          }]
        };
      }
    });
  }
  loadLaws(){
    this.recoService.getLaws({
      lawArea: '民间借贷'
    }).subscribe((res: any )=> {
      if(res.code == 200){
        this.laws = res.data.laws;
      }
    })
  }

  showCaseDetail(c: any , i , tpl){
    this.modalData.caseObj = c;
    this.modalData.index= i;
    this.openModal(tpl);
  }
  
  /**
   * 引用法条
   * @param c 
   * @param tpl 
   */
  showCaseLaws(c: any, tpl){
    this.modalData.caseObj = c;
    this.recoService.getCaseLaws(c.caseId).subscribe((res: any) => {
      if(res.code == 200){
        this.modalData.caselaws = res.data.caseLaws;
        this.openModal(tpl);
      }
    })
  }

  showCaseRules(c: any , tpl){
    this.modalData.caseObj = c;
    this.recoService.getCaseRules(c.caseKeys).subscribe((res: any) => {
      this.modalData.caseRules = res.data.caseRules;
      this.openModal(tpl);
    })
  }

  /**
   * 引用法律及司法解释： 查看法律条文
   */
  showLawDetail(law ,title, tpl){
    this.lawModal.law = law;
    this.lawModal.title = title;
    this.openModal(tpl);
  }

  showRuleExportWin(tpl){
    this.exportRules = this.rules.filter((r: any) => {
      return r.checked;
    });
    this.openModal(tpl);
  }

  copyRules(){

  }

  openLawbot(){
    var v5_chat_attrs	= "toolbar=0,scrollbars=0,location=0,menubar=0,resizable=1,top=" + (window.screen.availHeight - (window.screen.availHeight/2+275+40)) + ",left=" + (window.screen.availWidth - (window.screen.availWidth/2+365+20)) + ",width=730,height=550";
    window.open('/chatbot/desk/kehu.html?site_id=123898', '_blank', v5_chat_attrs);
  }

}
