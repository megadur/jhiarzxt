import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptAbr } from '../p-rezept-abr.model';
import { PRezeptAbrService } from '../service/p-rezept-abr.service';
import { PRezeptAbrDeleteDialogComponent } from '../delete/p-rezept-abr-delete-dialog.component';

@Component({
  selector: 'jhi-p-rezept-abr',
  templateUrl: './p-rezept-abr.component.html',
})
export class PRezeptAbrComponent implements OnInit {
  pRezeptAbrs?: IPRezeptAbr[];
  isLoading = false;

  constructor(protected pRezeptAbrService: PRezeptAbrService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pRezeptAbrService.query().subscribe(
      (res: HttpResponse<IPRezeptAbr[]>) => {
        this.isLoading = false;
        this.pRezeptAbrs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPRezeptAbr): number {
    return item.id!;
  }

  delete(pRezeptAbr: IPRezeptAbr): void {
    const modalRef = this.modalService.open(PRezeptAbrDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pRezeptAbr = pRezeptAbr;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
