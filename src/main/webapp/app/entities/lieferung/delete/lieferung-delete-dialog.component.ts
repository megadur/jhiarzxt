import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILieferung } from '../lieferung.model';
import { LieferungService } from '../service/lieferung.service';

@Component({
  templateUrl: './lieferung-delete-dialog.component.html',
})
export class LieferungDeleteDialogComponent {
  lieferung?: ILieferung;

  constructor(protected lieferungService: LieferungService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lieferungService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
