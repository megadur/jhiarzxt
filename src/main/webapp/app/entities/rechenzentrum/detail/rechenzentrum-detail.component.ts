import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRechenzentrum } from '../rechenzentrum.model';

@Component({
  selector: 'jhi-rechenzentrum-detail',
  templateUrl: './rechenzentrum-detail.component.html',
})
export class RechenzentrumDetailComponent implements OnInit {
  rechenzentrum: IRechenzentrum | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rechenzentrum }) => {
      this.rechenzentrum = rechenzentrum;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
