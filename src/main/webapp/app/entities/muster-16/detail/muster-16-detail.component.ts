import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMuster16 } from '../muster-16.model';

@Component({
  selector: 'jhi-muster-16-detail',
  templateUrl: './muster-16-detail.component.html',
})
export class Muster16DetailComponent implements OnInit {
  muster16: IMuster16 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16 }) => {
      this.muster16 = muster16;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
