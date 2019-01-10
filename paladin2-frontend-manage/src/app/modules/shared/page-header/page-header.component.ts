import {Component, ContentChild, HostBinding, Input, OnInit, TemplateRef} from '@angular/core';

@Component({
  selector: 'pld-page-header',
  templateUrl: './page-header.component.html',
  styleUrls: ['./page-header.component.less']
})
export class PageHeaderComponent implements OnInit {
  @HostBinding('class.pageHeader') bindPageHeader = true;

  @Input() title: string;

  @ContentChild('logo') logo: TemplateRef<any>;
  @ContentChild('titleArea') titleArea: TemplateRef<any>;
  @ContentChild('action') action: TemplateRef<any>;
  @ContentChild('content') content: TemplateRef<any>;
  @ContentChild('extraContent') extraContent: TemplateRef<any>;
  @ContentChild('tabs') tabs: TemplateRef<any>;
  constructor() { }

  ngOnInit() {
  }

}
