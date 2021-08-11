import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPWirkstoff } from '../p-wirkstoff.model';
import { PWirkstoffService } from '../service/p-wirkstoff.service';

@Component({
  templateUrl: './p-wirkstoff-delete-dialog.component.html',
})
export class PWirkstoffDeleteDialogComponent {
  pWirkstoff?: IPWirkstoff;

  constructor(protected pWirkstoffService: PWirkstoffService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pWirkstoffService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
