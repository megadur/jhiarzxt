import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IERezept } from '../e-rezept.model';

@Component({
  selector: 'jhi-e-rezept-detail',
  templateUrl: './e-rezept-detail.component.html',
})
export class ERezeptDetailComponent implements OnInit {
  eRezept: IERezept | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eRezept }) => {
      this.eRezept = eRezept;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
