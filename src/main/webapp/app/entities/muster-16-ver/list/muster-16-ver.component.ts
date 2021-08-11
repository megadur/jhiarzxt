import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Ver } from '../muster-16-ver.model';
import { Muster16VerService } from '../service/muster-16-ver.service';
import { Muster16VerDeleteDialogComponent } from '../delete/muster-16-ver-delete-dialog.component';

@Component({
  selector: 'jhi-muster-16-ver',
  templateUrl: './muster-16-ver.component.html',
})
export class Muster16VerComponent implements OnInit {
  muster16Vers?: IMuster16Ver[];
  isLoading = false;

  constructor(protected muster16VerService: Muster16VerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.muster16VerService.query().subscribe(
      (res: HttpResponse<IMuster16Ver[]>) => {
        this.isLoading = false;
        this.muster16Vers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMuster16Ver): number {
    return item.id!;
  }

  delete(muster16Ver: IMuster16Ver): void {
    const modalRef = this.modalService.open(Muster16VerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.muster16Ver = muster16Ver;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
