import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEAenderung } from '../e-aenderung.model';

@Component({
  selector: 'jhi-e-aenderung-detail',
  templateUrl: './e-aenderung-detail.component.html',
})
export class EAenderungDetailComponent implements OnInit {
  eAenderung: IEAenderung | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eAenderung }) => {
      this.eAenderung = eAenderung;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
