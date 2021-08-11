import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILieferung } from '../lieferung.model';

@Component({
  selector: 'jhi-lieferung-detail',
  templateUrl: './lieferung-detail.component.html',
})
export class LieferungDetailComponent implements OnInit {
  lieferung: ILieferung | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lieferung }) => {
      this.lieferung = lieferung;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
