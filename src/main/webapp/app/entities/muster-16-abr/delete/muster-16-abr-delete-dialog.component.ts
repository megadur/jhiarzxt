import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Abr } from '../muster-16-abr.model';
import { Muster16AbrService } from '../service/muster-16-abr.service';

@Component({
  templateUrl: './muster-16-abr-delete-dialog.component.html',
})
export class Muster16AbrDeleteDialogComponent {
  muster16Abr?: IMuster16Abr;

  constructor(protected muster16AbrService: Muster16AbrService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.muster16AbrService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
