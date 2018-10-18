import { Component, OnInit,TemplateRef  } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { MjjdService } from "./mjjd.service";

@Component({
  selector: 'app-reco',
  templateUrl: './mjjd.component.html',
  styleUrls: ['./mjjd.component.css']
})
export class MjjdComponent implements OnInit {

  constructor(private MjjdService: MjjdService,private modalService: BsModalService) { }

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
  rulesLoading = false;
  sameCasesLoading = false;
  lawsLoading = false;
  statusLoading = false;

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

  // activeCase:any = [{"caseContent":"中华人民共和国最高人民法院\n民 事 裁 定 书\n（2016）最高法民申3081号\n再审申请人（一审原告、二审上诉人）：叶君兰。\n委托代理人：朱正东，北京市蓝鹏律师事务所上海分所律师。\n被申请人（一审被告、二审被上诉人）：六安永翔钢材现货交易市场管理有限公司。住所地：安徽省六安市金安区三十铺镇。\n法定代表人：彭家雄，该公司董事长。\n被申请人（一审被告、二审被上诉人）：彭家雄。\n再审申请人叶君兰因与被申请人六安永翔钢材现货交易市场管理有限公司（以下简称六安公司）、彭家雄民间借贷纠纷一案，不服安徽省高级人民法院（2014）皖民二终字第00629号民事判决，向本院申请再审。本院依法组成合议庭进行了审查，现已审查终结。\n叶君兰向本院申请再审称：申请人提供了2011年7月4日的借款借据、该借款借据履行后2012年11月7日双方对账后的包括对200万元履行完毕的《确认书》、200万元由上海亮登贸易商行（以下简称亮登商行）改为上海岳坤贸易商行（以下简称岳坤商行）履行的《情况说明》、申请法院调查取得的证实被申请人在合同指定的账户、约定的时间点收到200万元的银行凭证。在二审期间还找到了原岳坤商行法定代表人郑传福出庭作证。至此，申请人提供了证明已经实际履行四份借据约定的向被申请人出借290万元的完整证据。而被申请人并没有向法院说明其指定的关联公司在2011年7月4日收到200万元是什么款项，亦未说明为何在2012年11月7日的《确认书》中认定共计收到申请人支付的290万元，其应当承担举证不能的不利后果。此外，二审判决不采信证人证言以及认为银行凭证中记载的“往来款”不属于借款亦错误。综上，根据《中华人民共和国民事诉讼法》第二百条之规定，请求对本案进行再审。\n本院经审查认为，本案系民间借贷纠纷。叶君兰主张其向六安公司及彭家雄实际出借了案涉200万元款项，并为此提交了其与六安公司及彭家雄之间的借款借据、亮登商行出具的情况说明、岳坤商行法定代表人的证人证言、彭家雄出具的认可收到200万元款项的确认书等证据加以证明。根据一、二审法院查明的事实，六安公司及彭家雄在借款借据中指定的付款账户确于借款借据约定之日收到岳坤商行汇入的200万元款项。六安公司及彭家雄称叶君兰并未对其出借200万元款项，但并未提供证据证明上述200万元款项系其他来源取得，对于叶君兰提交的证据亦未提供证据加以反驳。根据上述情况，一、二审判决认定叶君兰未向六安公司及彭家雄出借200万元，证据尚不充分。\n综上，叶君兰的再审申请符合《中华人民共和国民事诉讼法》第二百条第二项规定的情形。本院依照《中华人民共和国民事诉讼法》第二百零四条、第二百零六条、《最高人民法院关于适用〈中华人民共和国民事诉讼法〉的解释》第三百九十五条第一款之规定，裁定如下：\n指令安徽省高级人民法院再审本案；\n再审期间，中止原判决的执行。\n审　判　长　　任雪峰\n代理审判员　　王　朔\n代理审判员　　朱　科\n二〇一六年十二月二十七日\n书　记　员　　丁　一\n","caseDate":"2016-12-27","caseKeys":["借款发生在两被告夫妻关系存续期间","原告出具收条","原告出示鉴定书","双方未约定保证期间","双方约定借款利息","法院撤销判决","由被告提供担保向原告借款","被告出具借条","被告向原告借款","被告向第三方借款","被告因工程或业务对原告借款","被告对借款金额有异议","被告承担律师费","被告承担诉讼费","被告拖欠借款","被告支付利息","被告支付违约金","被告支付逾期利息"],"courtHear":"","courtDecision":"","caseId":808415,"caseName":"叶君兰与六安永翔钢材现货交易市场管理有限公司、彭家雄民间借贷纠纷申诉、申请民事裁定书"}];
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
    if(e != null){
      e.preventDefault(); 
    }
    this.caseTabIndex = i;
    if(i > 0)
      this.activeCase = this.sameCases['cases_leve' + i];
  }
  analyze(){
    this.caseKeysLoading = true;
    this.factors = [];
    this.MjjdService.calcFactors(this.caseText).subscribe((res: any) => {
      let data = res.data, code = res.code;
      if(code == 200){
        this.factors = data.caseKeys;  
        if(this.factors.length > 0){
          this.caseLoading = 3;
          this.loadCaseRules();
          this.loadLaws();
          this.loadStats();
        }
      }
      this.showFactors = true;
      this.caseKeysLoading = false;  
    })
  }

  loadCaseRules(){
    // this.caseLoading = 2;
    this.rulesLoading = true;
    
    if(this.factors.length == 0) return;
    this.MjjdService.getCaseRules(this.factors).subscribe((res: any) => {
      let data = res.data , code = res.code;
      if(code == 200){
        this.rules = data.caseRules;
      }
      this.rulesLoading = false;
      this.caseLoading --;
    });
    this.sameCasesLoading = true;
    this.MjjdService.getSameCases(this.factors).subscribe((res: any) => {
      let data = res.data , code = res.code;
      if(code == 200){
        this.sameCases = data.sameCases;
      }
      this.sameCasesLoading = false;
      
      this.caseLoading --;
      this.caseTabClick(null, 0);
    })
  }
  loadLaws(){
    this.lawsLoading = true;
    this.MjjdService.getLaws({
      lawArea: '民间借贷'
    }).subscribe((res: any )=> {
      if(res.code == 200){
        this.laws = res.data.laws;
      }
      this.lawsLoading = false;
      this.caseLoading --;
    })
  }
  loadStats(){
    let params = {
      caseKeys: this.factors,
      courtLevels: this.checkedCourt.map((c ,i) => c ? i + 1 : 0).filter(c => c > 0)
    };
    if(params.courtLevels.length <= 0) return;
    this.MjjdService.getStats(params).subscribe((res: any) => {
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
    this.MjjdService.getCaseLaws(c.caseId).subscribe((res: any) => {
      if(res.code == 200){
        this.modalData.caselaws = res.data.caseLaws;
        this.openModal(tpl);
      }
    })
  }

  showCaseRules(c: any , tpl){
    this.modalData.caseObj = c;
    this.MjjdService.getCaseRules(c.caseKeys).subscribe((res: any) => {
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
    // window.open('/chatbot/desk/kehu.html?site_id=123898', '_blank', v5_chat_attrs);
    window.open('http://desk.v5kf.com/desk/kehu.html?site_id=123898', '_blank', v5_chat_attrs);
  }

}
