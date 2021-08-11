import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LieferungComponent } from './list/lieferung.component';
import { LieferungDetailComponent } from './detail/lieferung-detail.component';
import { LieferungUpdateComponent } from './update/lieferung-update.component';
import { LieferungDeleteDialogComponent } from './delete/lieferung-delete-dialog.component';
import { LieferungRoutingModule } from './route/lieferung-routing.module';

@NgModule({
  imports: [SharedModule, LieferungRoutingModule],
  declarations: [LieferungComponent, LieferungDetailComponent, LieferungUpdateComponent, LieferungDeleteDialogComponent],
  entryComponents: [LieferungDeleteDialogComponent],
})
export class LieferungModule {}
