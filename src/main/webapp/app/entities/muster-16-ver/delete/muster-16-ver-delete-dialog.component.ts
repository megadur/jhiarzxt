import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Ver } from '../muster-16-ver.model';
import { Muster16VerService } from '../service/muster-16-ver.service';

@Component({
  templateUrl: './muster-16-ver-delete-dialog.component.html',
})
export class Muster16VerDeleteDialogComponent {
  muster16Ver?: IMuster16Ver;

  constructor(protected muster16VerService: Muster16VerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.muster16VerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
