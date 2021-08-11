import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { M16StatusComponent } from './list/m-16-status.component';
import { M16StatusDetailComponent } from './detail/m-16-status-detail.component';
import { M16StatusUpdateComponent } from './update/m-16-status-update.component';
import { M16StatusDeleteDialogComponent } from './delete/m-16-status-delete-dialog.component';
import { M16StatusRoutingModule } from './route/m-16-status-routing.module';

@NgModule({
  imports: [SharedModule, M16StatusRoutingModule],
  declarations: [M16StatusComponent, M16StatusDetailComponent, M16StatusUpdateComponent, M16StatusDeleteDialogComponent],
  entryComponents: [M16StatusDeleteDialogComponent],
})
export class M16StatusModule {}
