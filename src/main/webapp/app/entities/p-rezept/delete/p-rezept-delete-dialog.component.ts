import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPRezept } from '../p-rezept.model';
import { PRezeptService } from '../service/p-rezept.service';

@Component({
  templateUrl: './p-rezept-delete-dialog.component.html',
})
export class PRezeptDeleteDialogComponent {
  pRezept?: IPRezept;

  constructor(protected pRezeptService: PRezeptService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pRezeptService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
