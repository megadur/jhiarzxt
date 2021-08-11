import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPCharge } from '../p-charge.model';
import { PChargeService } from '../service/p-charge.service';

@Component({
  templateUrl: './p-charge-delete-dialog.component.html',
})
export class PChargeDeleteDialogComponent {
  pCharge?: IPCharge;

  constructor(protected pChargeService: PChargeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pChargeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
