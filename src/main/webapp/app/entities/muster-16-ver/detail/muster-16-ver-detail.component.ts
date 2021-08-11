import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMuster16Ver } from '../muster-16-ver.model';

@Component({
  selector: 'jhi-muster-16-ver-detail',
  templateUrl: './muster-16-ver-detail.component.html',
})
export class Muster16VerDetailComponent implements OnInit {
  muster16Ver: IMuster16Ver | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Ver }) => {
      this.muster16Ver = muster16Ver;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
