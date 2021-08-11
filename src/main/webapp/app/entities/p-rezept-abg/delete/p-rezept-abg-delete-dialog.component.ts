import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptAbg } from '../p-rezept-abg.model';
import { PRezeptAbgService } from '../service/p-rezept-abg.service';

@Component({
  templateUrl: './p-rezept-abg-delete-dialog.component.html',
})
export class PRezeptAbgDeleteDialogComponent {
  pRezeptAbg?: IPRezeptAbg;

  constructor(protected pRezeptAbgService: PRezeptAbgService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pRezeptAbgService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
