import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezeptAbr } from '../p-rezept-abr.model';
import { PRezeptAbrService } from '../service/p-rezept-abr.service';

@Component({
  templateUrl: './p-rezept-abr-delete-dialog.component.html',
})
export class PRezeptAbrDeleteDialogComponent {
  pRezeptAbr?: IPRezeptAbr;

  constructor(protected pRezeptAbrService: PRezeptAbrService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pRezeptAbrService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
