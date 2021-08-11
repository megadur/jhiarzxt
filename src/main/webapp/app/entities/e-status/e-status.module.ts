import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EStatusComponent } from './list/e-status.component';
import { EStatusDetailComponent } from './detail/e-status-detail.component';
import { EStatusUpdateComponent } from './update/e-status-update.component';
import { EStatusDeleteDialogComponent } from './delete/e-status-delete-dialog.component';
import { EStatusRoutingModule } from './route/e-status-routing.module';

@NgModule({
  imports: [SharedModule, EStatusRoutingModule],
  declarations: [EStatusComponent, EStatusDetailComponent, EStatusUpdateComponent, EStatusDeleteDialogComponent],
  entryComponents: [EStatusDeleteDialogComponent],
})
export class EStatusModule {}
