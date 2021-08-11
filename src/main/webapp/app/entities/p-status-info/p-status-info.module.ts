import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PStatusInfoComponent } from './list/p-status-info.component';
import { PStatusInfoDetailComponent } from './detail/p-status-info-detail.component';
import { PStatusInfoUpdateComponent } from './update/p-status-info-update.component';
import { PStatusInfoDeleteDialogComponent } from './delete/p-status-info-delete-dialog.component';
import { PStatusInfoRoutingModule } from './route/p-status-info-routing.module';

@NgModule({
  imports: [SharedModule, PStatusInfoRoutingModule],
  declarations: [PStatusInfoComponent, PStatusInfoDetailComponent, PStatusInfoUpdateComponent, PStatusInfoDeleteDialogComponent],
  entryComponents: [PStatusInfoDeleteDialogComponent],
})
export class PStatusInfoModule {}
