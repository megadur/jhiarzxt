import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptAbg } from '../p-rezept-abg.model';
import { PRezeptAbgService } from '../service/p-rezept-abg.service';
import { PRezeptAbgDeleteDialogComponent } from '../delete/p-rezept-abg-delete-dialog.component';

@Component({
  selector: 'jhi-p-rezept-abg',
  templateUrl: './p-rezept-abg.component.html',
})
export class PRezeptAbgComponent implements OnInit {
  pRezeptAbgs?: IPRezeptAbg[];
  isLoading = false;

  constructor(protected pRezeptAbgService: PRezeptAbgService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pRezeptAbgService.query().subscribe(
      (res: HttpResponse<IPRezeptAbg[]>) => {
        this.isLoading = false;
        this.pRezeptAbgs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPRezeptAbg): number {
    return item.id!;
  }

  delete(pRezeptAbg: IPRezeptAbg): void {
    const modalRef = this.modalService.open(PRezeptAbgDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pRezeptAbg = pRezeptAbg;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
