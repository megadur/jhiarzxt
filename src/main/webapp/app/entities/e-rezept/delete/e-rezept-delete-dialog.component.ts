import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IERezept } from '../e-rezept.model';
import { ERezeptService } from '../service/e-rezept.service';

@Component({
  templateUrl: './e-rezept-delete-dialog.component.html',
})
export class ERezeptDeleteDialogComponent {
  eRezept?: IERezept;

  constructor(protected eRezeptService: ERezeptService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eRezeptService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
