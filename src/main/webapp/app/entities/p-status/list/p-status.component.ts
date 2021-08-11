import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPStatus } from '../p-status.model';
import { PStatusService } from '../service/p-status.service';
import { PStatusDeleteDialogComponent } from '../delete/p-status-delete-dialog.component';

@Component({
  selector: 'jhi-p-status',
  templateUrl: './p-status.component.html',
})
export class PStatusComponent implements OnInit {
  pStatuses?: IPStatus[];
  isLoading = false;

  constructor(protected pStatusService: PStatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pStatusService.query().subscribe(
      (res: HttpResponse<IPStatus[]>) => {
        this.isLoading = false;
        this.pStatuses = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPStatus): number {
    return item.id!;
  }

  delete(pStatus: IPStatus): void {
    const modalRef = this.modalService.open(PStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pStatus = pStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
