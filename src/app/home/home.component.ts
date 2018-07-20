import { Component, OnInit } from '@angular/core';
import { HomeService } from "./home.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private homeService: HomeService,private route: ActivatedRoute) { }


  caseType: string = null;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.info(params);
    })
    this.route.params.subscribe(params => {
      this.caseType = params.caseType;
    });
  }



}
