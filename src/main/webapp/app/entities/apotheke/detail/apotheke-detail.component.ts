import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApotheke } from '../apotheke.model';

@Component({
  selector: 'jhi-apotheke-detail',
  templateUrl: './apotheke-detail.component.html',
})
export class ApothekeDetailComponent implements OnInit {
  apotheke: IApotheke | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apotheke }) => {
      this.apotheke = apotheke;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
