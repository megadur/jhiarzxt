import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPRezeptAbg } from '../p-rezept-abg.model';

@Component({
  selector: 'jhi-p-rezept-abg-detail',
  templateUrl: './p-rezept-abg-detail.component.html',
})
export class PRezeptAbgDetailComponent implements OnInit {
  pRezeptAbg: IPRezeptAbg | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptAbg }) => {
      this.pRezeptAbg = pRezeptAbg;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
