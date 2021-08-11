import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Abr } from '../muster-16-abr.model';
import { Muster16AbrService } from '../service/muster-16-abr.service';
import { Muster16AbrDeleteDialogComponent } from '../delete/muster-16-abr-delete-dialog.component';

@Component({
  selector: 'jhi-muster-16-abr',
  templateUrl: './muster-16-abr.component.html',
})
export class Muster16AbrComponent implements OnInit {
  muster16Abrs?: IMuster16Abr[];
  isLoading = false;

  constructor(protected muster16AbrService: Muster16AbrService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.muster16AbrService.query().subscribe(
      (res: HttpResponse<IMuster16Abr[]>) => {
        this.isLoading = false;
        this.muster16Abrs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMuster16Abr): number {
    return item.id!;
  }

  delete(muster16Abr: IMuster16Abr): void {
    const modalRef = this.modalService.open(Muster16AbrDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.muster16Abr = muster16Abr;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
