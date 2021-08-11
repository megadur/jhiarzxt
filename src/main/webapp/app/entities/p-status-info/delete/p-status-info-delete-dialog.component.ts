import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPStatusInfo } from '../p-status-info.model';
import { PStatusInfoService } from '../service/p-status-info.service';

@Component({
  templateUrl: './p-status-info-delete-dialog.component.html',
})
export class PStatusInfoDeleteDialogComponent {
  pStatusInfo?: IPStatusInfo;

  constructor(protected pStatusInfoService: PStatusInfoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pStatusInfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
