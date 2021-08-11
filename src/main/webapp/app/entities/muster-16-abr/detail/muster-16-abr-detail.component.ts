import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMuster16Abr } from '../muster-16-abr.model';

@Component({
  selector: 'jhi-muster-16-abr-detail',
  templateUrl: './muster-16-abr-detail.component.html',
})
export class Muster16AbrDetailComponent implements OnInit {
  muster16Abr: IMuster16Abr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Abr }) => {
      this.muster16Abr = muster16Abr;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
