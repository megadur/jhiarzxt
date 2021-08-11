import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Abg } from '../muster-16-abg.model';
import { Muster16AbgService } from '../service/muster-16-abg.service';
import { Muster16AbgDeleteDialogComponent } from '../delete/muster-16-abg-delete-dialog.component';

@Component({
  selector: 'jhi-muster-16-abg',
  templateUrl: './muster-16-abg.component.html',
})
export class Muster16AbgComponent implements OnInit {
  muster16Abgs?: IMuster16Abg[];
  isLoading = false;

  constructor(protected muster16AbgService: Muster16AbgService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.muster16AbgService.query().subscribe(
      (res: HttpResponse<IMuster16Abg[]>) => {
        this.isLoading = false;
        this.muster16Abgs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMuster16Abg): number {
    return item.id!;
  }

  delete(muster16Abg: IMuster16Abg): void {
    const modalRef = this.modalService.open(Muster16AbgDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.muster16Abg = muster16Abg;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
