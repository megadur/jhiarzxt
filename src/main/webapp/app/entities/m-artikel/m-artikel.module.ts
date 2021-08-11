import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MArtikelComponent } from './list/m-artikel.component';
import { MArtikelDetailComponent } from './detail/m-artikel-detail.component';
import { MArtikelUpdateComponent } from './update/m-artikel-update.component';
import { MArtikelDeleteDialogComponent } from './delete/m-artikel-delete-dialog.component';
import { MArtikelRoutingModule } from './route/m-artikel-routing.module';

@NgModule({
  imports: [SharedModule, MArtikelRoutingModule],
  declarations: [MArtikelComponent, MArtikelDetailComponent, MArtikelUpdateComponent, MArtikelDeleteDialogComponent],
  entryComponents: [MArtikelDeleteDialogComponent],
})
export class MArtikelModule {}
