import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRechenzentrum } from '../rechenzentrum.model';
import { RechenzentrumService } from '../service/rechenzentrum.service';

@Component({
  templateUrl: './rechenzentrum-delete-dialog.component.html',
})
export class RechenzentrumDeleteDialogComponent {
  rechenzentrum?: IRechenzentrum;

  constructor(protected rechenzentrumService: RechenzentrumService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rechenzentrumService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
