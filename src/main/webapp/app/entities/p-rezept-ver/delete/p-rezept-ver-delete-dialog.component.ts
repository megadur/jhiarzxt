import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptVer } from '../p-rezept-ver.model';
import { PRezeptVerService } from '../service/p-rezept-ver.service';

@Component({
  templateUrl: './p-rezept-ver-delete-dialog.component.html',
})
export class PRezeptVerDeleteDialogComponent {
  pRezeptVer?: IPRezeptVer;

  constructor(protected pRezeptVerService: PRezeptVerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pRezeptVerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
