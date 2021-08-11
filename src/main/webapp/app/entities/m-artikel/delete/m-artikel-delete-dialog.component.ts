import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMArtikel } from '../m-artikel.model';
import { MArtikelService } from '../service/m-artikel.service';

@Component({
  templateUrl: './m-artikel-delete-dialog.component.html',
})
export class MArtikelDeleteDialogComponent {
  mArtikel?: IMArtikel;

  constructor(protected mArtikelService: MArtikelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mArtikelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
