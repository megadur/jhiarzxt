import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApotheke } from '../apotheke.model';
import { ApothekeService } from '../service/apotheke.service';
import { ApothekeDeleteDialogComponent } from '../delete/apotheke-delete-dialog.component';

@Component({
  selector: 'jhi-apotheke',
  templateUrl: './apotheke.component.html',
})
export class ApothekeComponent implements OnInit {
  apothekes?: IApotheke[];
  isLoading = false;

  constructor(protected apothekeService: ApothekeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.apothekeService.query().subscribe(
      (res: HttpResponse<IApotheke[]>) => {
        this.isLoading = false;
        this.apothekes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IApotheke): number {
    return item.id!;
  }

  delete(apotheke: IApotheke): void {
    const modalRef = this.modalService.open(ApothekeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.apotheke = apotheke;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
