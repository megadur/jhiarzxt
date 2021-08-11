import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IM16Status } from '../m-16-status.model';
import { M16StatusService } from '../service/m-16-status.service';

@Component({
  templateUrl: './m-16-status-delete-dialog.component.html',
})
export class M16StatusDeleteDialogComponent {
  m16Status?: IM16Status;

  constructor(protected m16StatusService: M16StatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.m16StatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
