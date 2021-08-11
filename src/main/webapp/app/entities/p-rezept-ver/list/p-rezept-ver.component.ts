import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptVer } from '../p-rezept-ver.model';
import { PRezeptVerService } from '../service/p-rezept-ver.service';
import { PRezeptVerDeleteDialogComponent } from '../delete/p-rezept-ver-delete-dialog.component';

@Component({
  selector: 'jhi-p-rezept-ver',
  templateUrl: './p-rezept-ver.component.html',
})
export class PRezeptVerComponent implements OnInit {
  pRezeptVers?: IPRezeptVer[];
  isLoading = false;

  constructor(protected pRezeptVerService: PRezeptVerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pRezeptVerService.query().subscribe(
      (res: HttpResponse<IPRezeptVer[]>) => {
        this.isLoading = false;
        this.pRezeptVers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPRezeptVer): number {
    return item.id!;
  }

  delete(pRezeptVer: IPRezeptVer): void {
    const modalRef = this.modalService.open(PRezeptVerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pRezeptVer = pRezeptVer;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
