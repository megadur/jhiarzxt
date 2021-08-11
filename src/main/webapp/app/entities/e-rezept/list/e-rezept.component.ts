import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IERezept } from '../e-rezept.model';
import { ERezeptService } from '../service/e-rezept.service';
import { ERezeptDeleteDialogComponent } from '../delete/e-rezept-delete-dialog.component';

@Component({
  selector: 'jhi-e-rezept',
  templateUrl: './e-rezept.component.html',
})
export class ERezeptComponent implements OnInit {
  eRezepts?: IERezept[];
  isLoading = false;

  constructor(protected eRezeptService: ERezeptService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.eRezeptService.query().subscribe(
      (res: HttpResponse<IERezept[]>) => {
        this.isLoading = false;
        this.eRezepts = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IERezept): number {
    return item.id!;
  }

  delete(eRezept: IERezept): void {
    const modalRef = this.modalService.open(ERezeptDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eRezept = eRezept;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
