import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPStatus } from '../p-status.model';
import { PStatusService } from '../service/p-status.service';

@Component({
  templateUrl: './p-status-delete-dialog.component.html',
})
export class PStatusDeleteDialogComponent {
  pStatus?: IPStatus;

  constructor(protected pStatusService: PStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
