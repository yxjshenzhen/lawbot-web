import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ActivatedRoute} from "@angular/router";
import { AwardService } from '../award.service';

@Component({
  selector: 'app-award',
  templateUrl: './award.component.html',
  styleUrls: ['./award.component.css']
})
export class AwardComponent implements OnInit {

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.params.caseType = params.caseType
    });
  }

  constructor(private http: HttpClient,private route: ActivatedRoute,private awardService: AwardService){
    
  }

  params: any = {
    caseType: null,
    routineFile: null,//程序部分
    appDocFile: null,//仲裁申请书
    eAppFiles: [], //申请人证据
    resDocFile: null,//答辩状
    eResFiles: [] //被申请人证据
  }

  docRes: any = {
    errors: [],
    warns: []
  }

  onAddSingleFile(target ,e){
    this.params[target] = e.target.files[0];
  }

  onAddMultiFile(target , e){
    let files = e.target.files;
    for(let i = 0 ; i < files.length ; i ++){
      target.push(files[i]);
    }
  }
  onRemoveFiles(target , file){
    let i = target.indexOf(file);
    target.splice(i ,1);
  }

  onGenerate(){
    let formData = new FormData();
    formData.append("caseType" , this.params.caseType);

    formData.append("routineFile" , this.params.routineFile);
    formData.append("appDocFile" , this.params.appDocFile);

    this.params.eAppFiles.forEach(f => {
      formData.append("eAppFiles" , f);
    });

    formData.append("resDocFile" , this.params.resDocFile);

    this.params.eResFiles.forEach(f => {
      formData.append("eResFiles" , f);
    });


    this.docRes = {
      errors: [], warns: []
    }

    this.awardService.generateAwardDoc(formData).subscribe((res: any) => {
      if(res.code == 200){
        window.open('api/file/' + res.data.fileName, '_blank');
      }else if(res.code == 601){
        this.docRes = res.data.doc;
        if(this.docRes.errors.length <= 0 && this.docRes.fileName){
          window.open('api/file/' + this.docRes.fileName, '_blank');
        }
      }
    });
  }

  openLawbot(){
    var v5_chat_attrs	= "toolbar=0,scrollbars=0,location=0,menubar=0,resizable=1,top=" + (window.screen.availHeight - (window.screen.availHeight/2+275+40)) + ",left=" + (window.screen.availWidth - (window.screen.availWidth/2+365+20)) + ",width=730,height=550";
    window.open('http://desk.v5kf.com/desk/kehu.html?site_id=123898', '_blank', v5_chat_attrs);
  }

}
