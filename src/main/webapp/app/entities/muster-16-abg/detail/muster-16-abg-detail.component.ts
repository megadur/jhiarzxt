import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMuster16Abg } from '../muster-16-abg.model';

@Component({
  selector: 'jhi-muster-16-abg-detail',
  templateUrl: './muster-16-abg-detail.component.html',
})
export class Muster16AbgDetailComponent implements OnInit {
  muster16Abg: IMuster16Abg | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Abg }) => {
      this.muster16Abg = muster16Abg;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
