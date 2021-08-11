import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMArtikel } from '../m-artikel.model';

@Component({
  selector: 'jhi-m-artikel-detail',
  templateUrl: './m-artikel-detail.component.html',
})
export class MArtikelDetailComponent implements OnInit {
  mArtikel: IMArtikel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mArtikel }) => {
      this.mArtikel = mArtikel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
