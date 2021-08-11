import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMArtikel } from '../m-artikel.model';
import { MArtikelService } from '../service/m-artikel.service';
import { MArtikelDeleteDialogComponent } from '../delete/m-artikel-delete-dialog.component';

@Component({
  selector: 'jhi-m-artikel',
  templateUrl: './m-artikel.component.html',
})
export class MArtikelComponent implements OnInit {
  mArtikels?: IMArtikel[];
  isLoading = false;

  constructor(protected mArtikelService: MArtikelService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.mArtikelService.query().subscribe(
      (res: HttpResponse<IMArtikel[]>) => {
        this.isLoading = false;
        this.mArtikels = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMArtikel): number {
    return item.id!;
  }

  delete(mArtikel: IMArtikel): void {
    const modalRef = this.modalService.open(MArtikelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mArtikel = mArtikel;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
