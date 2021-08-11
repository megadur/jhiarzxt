import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPRezeptAbr } from '../p-rezept-abr.model';

@Component({
  selector: 'jhi-p-rezept-abr-detail',
  templateUrl: './p-rezept-abr-detail.component.html',
})
export class PRezeptAbrDetailComponent implements OnInit {
  pRezeptAbr: IPRezeptAbr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptAbr }) => {
      this.pRezeptAbr = pRezeptAbr;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
