import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16 } from '../muster-16.model';
import { Muster16Service } from '../service/muster-16.service';

@Component({
  templateUrl: './muster-16-delete-dialog.component.html',
})
export class Muster16DeleteDialogComponent {
  muster16?: IMuster16;

  constructor(protected muster16Service: Muster16Service, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.muster16Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
