import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IM16Status } from '../m-16-status.model';
import { M16StatusService } from '../service/m-16-status.service';
import { M16StatusDeleteDialogComponent } from '../delete/m-16-status-delete-dialog.component';

@Component({
  selector: 'jhi-m-16-status',
  templateUrl: './m-16-status.component.html',
})
export class M16StatusComponent implements OnInit {
  m16Statuses?: IM16Status[];
  isLoading = false;

  constructor(protected m16StatusService: M16StatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.m16StatusService.query().subscribe(
      (res: HttpResponse<IM16Status[]>) => {
        this.isLoading = false;
        this.m16Statuses = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IM16Status): number {
    return item.id!;
  }

  delete(m16Status: IM16Status): void {
    const modalRef = this.modalService.open(M16StatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.m16Status = m16Status;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
