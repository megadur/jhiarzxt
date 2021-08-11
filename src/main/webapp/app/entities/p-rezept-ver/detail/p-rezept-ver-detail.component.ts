import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPRezeptVer } from '../p-rezept-ver.model';

@Component({
  selector: 'jhi-p-rezept-ver-detail',
  templateUrl: './p-rezept-ver-detail.component.html',
})
export class PRezeptVerDetailComponent implements OnInit {
  pRezeptVer: IPRezeptVer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptVer }) => {
      this.pRezeptVer = pRezeptVer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
