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

  laws:any = {};

  activeCase:any = [];

  geoOptions = {
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
            data:[
              {name: "西藏", value: 5},{name: "青海", value: 1},{name: "宁夏", value: 44},{name: "海南", value: 1},{name: "甘肃", value: 36},{name: "贵州", value: 7},
              {name: "新疆", value: 34},{name: "云南", value: 68},{name: "重庆", value: 42},{name: "吉林", value: 25},{name: "山西", value: 20},{name: "天津", value: 7},
              {name: "江西", value: 96},{name: "广西", value: 9},{name: "陕西", value: 98},{name: "黑龙江", value: 44},{name: "内蒙古", value: 18},{name: "安徽", value: 189},
              {name: "北京", value: 0},{name: "福建", value: 357},{name: "上海", value: 1},{name: "湖北", value: 91},{name: "湖南", value: 141},{name: "四川", value: 108},{name: "辽宁", value: 68},
              {name: "河北", value: 112},{name: "河南", value: 200},{name: "浙江", value: 92},{name: "山东", value: 249},{name: "江苏", value: 195},{name: "广东", value: 23}
            ]
        }
    ],								    
    animation: false
  };
  pieOptions = {
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
        data: ['最高院','高级法院','中级法院','基层法院']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data: [{name: "最高院", value: 0}, {name: "高级法院", value: 0}, {name: "中级法院", value: 2381},{name: "基层法院", value: 0}],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
        }
    ]
  }

  ngOnInit() {
    
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
        this.loadLaws();
        this.loadCaseRules();
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

}
