import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPStatusInfo } from '../p-status-info.model';
import { PStatusInfoService } from '../service/p-status-info.service';
import { PStatusInfoDeleteDialogComponent } from '../delete/p-status-info-delete-dialog.component';

@Component({
  selector: 'jhi-p-status-info',
  templateUrl: './p-status-info.component.html',
})
export class PStatusInfoComponent implements OnInit {
  pStatusInfos?: IPStatusInfo[];
  isLoading = false;

  constructor(protected pStatusInfoService: PStatusInfoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pStatusInfoService.query().subscribe(
      (res: HttpResponse<IPStatusInfo[]>) => {
        this.isLoading = false;
        this.pStatusInfos = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPStatusInfo): number {
    return item.id!;
  }

  delete(pStatusInfo: IPStatusInfo): void {
    const modalRef = this.modalService.open(PStatusInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pStatusInfo = pStatusInfo;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
