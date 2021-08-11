import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PStatusComponent } from './list/p-status.component';
import { PStatusDetailComponent } from './detail/p-status-detail.component';
import { PStatusUpdateComponent } from './update/p-status-update.component';
import { PStatusDeleteDialogComponent } from './delete/p-status-delete-dialog.component';
import { PStatusRoutingModule } from './route/p-status-routing.module';

@NgModule({
  imports: [SharedModule, PStatusRoutingModule],
  declarations: [PStatusComponent, PStatusDetailComponent, PStatusUpdateComponent, PStatusDeleteDialogComponent],
  entryComponents: [PStatusDeleteDialogComponent],
})
export class PStatusModule {}
