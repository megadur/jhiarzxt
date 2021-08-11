import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILieferung } from '../lieferung.model';
import { LieferungService } from '../service/lieferung.service';
import { LieferungDeleteDialogComponent } from '../delete/lieferung-delete-dialog.component';

@Component({
  selector: 'jhi-lieferung',
  templateUrl: './lieferung.component.html',
})
export class LieferungComponent implements OnInit {
  lieferungs?: ILieferung[];
  isLoading = false;

  constructor(protected lieferungService: LieferungService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.lieferungService.query().subscribe(
      (res: HttpResponse<ILieferung[]>) => {
        this.isLoading = false;
        this.lieferungs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILieferung): number {
    return item.id!;
  }

  delete(lieferung: ILieferung): void {
    const modalRef = this.modalService.open(LieferungDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lieferung = lieferung;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
