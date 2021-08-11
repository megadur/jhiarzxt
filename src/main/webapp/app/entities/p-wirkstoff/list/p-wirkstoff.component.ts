import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPWirkstoff } from '../p-wirkstoff.model';
import { PWirkstoffService } from '../service/p-wirkstoff.service';
import { PWirkstoffDeleteDialogComponent } from '../delete/p-wirkstoff-delete-dialog.component';

@Component({
  selector: 'jhi-p-wirkstoff',
  templateUrl: './p-wirkstoff.component.html',
})
export class PWirkstoffComponent implements OnInit {
  pWirkstoffs?: IPWirkstoff[];
  isLoading = false;

  constructor(protected pWirkstoffService: PWirkstoffService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pWirkstoffService.query().subscribe(
      (res: HttpResponse<IPWirkstoff[]>) => {
        this.isLoading = false;
        this.pWirkstoffs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPWirkstoff): number {
    return item.id!;
  }

  delete(pWirkstoff: IPWirkstoff): void {
    const modalRef = this.modalService.open(PWirkstoffDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pWirkstoff = pWirkstoff;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
