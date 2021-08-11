import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEAenderung } from '../e-aenderung.model';
import { EAenderungService } from '../service/e-aenderung.service';
import { EAenderungDeleteDialogComponent } from '../delete/e-aenderung-delete-dialog.component';

@Component({
  selector: 'jhi-e-aenderung',
  templateUrl: './e-aenderung.component.html',
})
export class EAenderungComponent implements OnInit {
  eAenderungs?: IEAenderung[];
  isLoading = false;

  constructor(protected eAenderungService: EAenderungService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.eAenderungService.query().subscribe(
      (res: HttpResponse<IEAenderung[]>) => {
        this.isLoading = false;
        this.eAenderungs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEAenderung): number {
    return item.id!;
  }

  delete(eAenderung: IEAenderung): void {
    const modalRef = this.modalService.open(EAenderungDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eAenderung = eAenderung;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
