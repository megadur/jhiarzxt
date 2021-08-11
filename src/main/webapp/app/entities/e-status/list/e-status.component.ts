import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEStatus } from '../e-status.model';
import { EStatusService } from '../service/e-status.service';
import { EStatusDeleteDialogComponent } from '../delete/e-status-delete-dialog.component';

@Component({
  selector: 'jhi-e-status',
  templateUrl: './e-status.component.html',
})
export class EStatusComponent implements OnInit {
  eStatuses?: IEStatus[];
  isLoading = false;

  constructor(protected eStatusService: EStatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.eStatusService.query().subscribe(
      (res: HttpResponse<IEStatus[]>) => {
        this.isLoading = false;
        this.eStatuses = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEStatus): number {
    return item.id!;
  }

  delete(eStatus: IEStatus): void {
    const modalRef = this.modalService.open(EStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eStatus = eStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
