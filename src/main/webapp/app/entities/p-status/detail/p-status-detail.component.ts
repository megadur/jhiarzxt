import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPStatus } from '../p-status.model';

@Component({
  selector: 'jhi-p-status-detail',
  templateUrl: './p-status-detail.component.html',
})
export class PStatusDetailComponent implements OnInit {
  pStatus: IPStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pStatus }) => {
      this.pStatus = pStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
