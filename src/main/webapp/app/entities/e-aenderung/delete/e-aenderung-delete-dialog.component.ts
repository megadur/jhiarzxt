import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEAenderung } from '../e-aenderung.model';
import { EAenderungService } from '../service/e-aenderung.service';

@Component({
  templateUrl: './e-aenderung-delete-dialog.component.html',
})
export class EAenderungDeleteDialogComponent {
  eAenderung?: IEAenderung;

  constructor(protected eAenderungService: EAenderungService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eAenderungService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
