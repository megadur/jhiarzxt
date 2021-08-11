import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPWirkstoff } from '../p-wirkstoff.model';

@Component({
  selector: 'jhi-p-wirkstoff-detail',
  templateUrl: './p-wirkstoff-detail.component.html',
})
export class PWirkstoffDetailComponent implements OnInit {
  pWirkstoff: IPWirkstoff | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pWirkstoff }) => {
      this.pWirkstoff = pWirkstoff;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
