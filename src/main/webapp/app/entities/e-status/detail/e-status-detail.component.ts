import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEStatus } from '../e-status.model';

@Component({
  selector: 'jhi-e-status-detail',
  templateUrl: './e-status-detail.component.html',
})
export class EStatusDetailComponent implements OnInit {
  eStatus: IEStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eStatus }) => {
      this.eStatus = eStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
