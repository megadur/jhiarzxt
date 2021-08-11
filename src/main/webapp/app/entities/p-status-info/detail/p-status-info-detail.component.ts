import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPStatusInfo } from '../p-status-info.model';

@Component({
  selector: 'jhi-p-status-info-detail',
  templateUrl: './p-status-info-detail.component.html',
})
export class PStatusInfoDetailComponent implements OnInit {
  pStatusInfo: IPStatusInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pStatusInfo }) => {
      this.pStatusInfo = pStatusInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
