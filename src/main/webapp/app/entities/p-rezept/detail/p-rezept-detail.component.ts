import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPRezept } from '../p-rezept.model';

@Component({
  selector: 'jhi-p-rezept-detail',
  templateUrl: './p-rezept-detail.component.html',
})
export class PRezeptDetailComponent implements OnInit {
  pRezept: IPRezept | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezept }) => {
      this.pRezept = pRezept;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
