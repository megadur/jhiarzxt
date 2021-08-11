import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuster16Abg } from '../muster-16-abg.model';
import { Muster16AbgService } from '../service/muster-16-abg.service';

@Component({
  templateUrl: './muster-16-abg-delete-dialog.component.html',
})
export class Muster16AbgDeleteDialogComponent {
  muster16Abg?: IMuster16Abg;

  constructor(protected muster16AbgService: Muster16AbgService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.muster16AbgService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
