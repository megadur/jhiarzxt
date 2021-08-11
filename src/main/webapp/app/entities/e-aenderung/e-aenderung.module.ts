import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EAenderungComponent } from './list/e-aenderung.component';
import { EAenderungDetailComponent } from './detail/e-aenderung-detail.component';
import { EAenderungUpdateComponent } from './update/e-aenderung-update.component';
import { EAenderungDeleteDialogComponent } from './delete/e-aenderung-delete-dialog.component';
import { EAenderungRoutingModule } from './route/e-aenderung-routing.module';

@NgModule({
  imports: [SharedModule, EAenderungRoutingModule],
  declarations: [EAenderungComponent, EAenderungDetailComponent, EAenderungUpdateComponent, EAenderungDeleteDialogComponent],
  entryComponents: [EAenderungDeleteDialogComponent],
})
export class EAenderungModule {}
