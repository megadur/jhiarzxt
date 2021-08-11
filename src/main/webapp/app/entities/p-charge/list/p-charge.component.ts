import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPCharge } from '../p-charge.model';
import { PChargeService } from '../service/p-charge.service';
import { PChargeDeleteDialogComponent } from '../delete/p-charge-delete-dialog.component';

@Component({
  selector: 'jhi-p-charge',
  templateUrl: './p-charge.component.html',
})
export class PChargeComponent implements OnInit {
  pCharges?: IPCharge[];
  isLoading = false;

  constructor(protected pChargeService: PChargeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pChargeService.query().subscribe(
      (res: HttpResponse<IPCharge[]>) => {
        this.isLoading = false;
        this.pCharges = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPCharge): number {
    return item.id!;
  }

  delete(pCharge: IPCharge): void {
    const modalRef = this.modalService.open(PChargeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pCharge = pCharge;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
