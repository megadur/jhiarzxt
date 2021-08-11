import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApotheke } from '../apotheke.model';
import { ApothekeService } from '../service/apotheke.service';

@Component({
  templateUrl: './apotheke-delete-dialog.component.html',
})
export class ApothekeDeleteDialogComponent {
  apotheke?: IApotheke;

  constructor(protected apothekeService: ApothekeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apothekeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
