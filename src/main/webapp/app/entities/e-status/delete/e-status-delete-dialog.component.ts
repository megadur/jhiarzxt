import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEStatus } from '../e-status.model';
import { EStatusService } from '../service/e-status.service';

@Component({
  templateUrl: './e-status-delete-dialog.component.html',
})
export class EStatusDeleteDialogComponent {
  eStatus?: IEStatus;

  constructor(protected eStatusService: EStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
