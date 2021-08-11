import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16 } from '../muster-16.model';
import { Muster16Service } from '../service/muster-16.service';
import { Muster16DeleteDialogComponent } from '../delete/muster-16-delete-dialog.component';

@Component({
  selector: 'jhi-muster-16',
  templateUrl: './muster-16.component.html',
})
export class Muster16Component implements OnInit {
  muster16s?: IMuster16[];
  isLoading = false;

  constructor(protected muster16Service: Muster16Service, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.muster16Service.query().subscribe(
      (res: HttpResponse<IMuster16[]>) => {
        this.isLoading = false;
        this.muster16s = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMuster16): number {
    return item.id!;
  }

  delete(muster16: IMuster16): void {
    const modalRef = this.modalService.open(Muster16DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.muster16 = muster16;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
