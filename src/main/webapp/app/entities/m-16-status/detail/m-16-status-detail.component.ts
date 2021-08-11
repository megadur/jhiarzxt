import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IM16Status } from '../m-16-status.model';

@Component({
  selector: 'jhi-m-16-status-detail',
  templateUrl: './m-16-status-detail.component.html',
})
export class M16StatusDetailComponent implements OnInit {
  m16Status: IM16Status | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ m16Status }) => {
      this.m16Status = m16Status;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
