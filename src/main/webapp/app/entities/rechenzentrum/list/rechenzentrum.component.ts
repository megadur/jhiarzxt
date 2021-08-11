import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRechenzentrum } from '../rechenzentrum.model';
import { RechenzentrumService } from '../service/rechenzentrum.service';
import { RechenzentrumDeleteDialogComponent } from '../delete/rechenzentrum-delete-dialog.component';

@Component({
  selector: 'jhi-rechenzentrum',
  templateUrl: './rechenzentrum.component.html',
})
export class RechenzentrumComponent implements OnInit {
  rechenzentrums?: IRechenzentrum[];
  isLoading = false;

  constructor(protected rechenzentrumService: RechenzentrumService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.rechenzentrumService.query().subscribe(
      (res: HttpResponse<IRechenzentrum[]>) => {
        this.isLoading = false;
        this.rechenzentrums = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRechenzentrum): number {
    return item.id!;
  }

  delete(rechenzentrum: IRechenzentrum): void {
    const modalRef = this.modalService.open(RechenzentrumDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rechenzentrum = rechenzentrum;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
