import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPCharge } from '../p-charge.model';

@Component({
  selector: 'jhi-p-charge-detail',
  templateUrl: './p-charge-detail.component.html',
})
export class PChargeDetailComponent implements OnInit {
  pCharge: IPCharge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pCharge }) => {
      this.pCharge = pCharge;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
