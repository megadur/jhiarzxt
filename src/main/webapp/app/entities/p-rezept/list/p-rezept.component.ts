import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezept } from '../p-rezept.model';
import { PRezeptService } from '../service/p-rezept.service';
import { PRezeptDeleteDialogComponent } from '../delete/p-rezept-delete-dialog.component';

@Component({
  selector: 'jhi-p-rezept',
  templateUrl: './p-rezept.component.html',
})
export class PRezeptComponent implements OnInit {
  pRezepts?: IPRezept[];
  isLoading = false;

  constructor(protected pRezeptService: PRezeptService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.pRezeptService.query().subscribe(
      (res: HttpResponse<IPRezept[]>) => {
        this.isLoading = false;
        this.pRezepts = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPRezept): number {
    return item.id!;
  }

  delete(pRezept: IPRezept): void {
    const modalRef = this.modalService.open(PRezeptDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pRezept = pRezept;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
