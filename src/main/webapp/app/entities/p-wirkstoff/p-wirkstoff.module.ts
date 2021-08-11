import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PWirkstoffComponent } from './list/p-wirkstoff.component';
import { PWirkstoffDetailComponent } from './detail/p-wirkstoff-detail.component';
import { PWirkstoffUpdateComponent } from './update/p-wirkstoff-update.component';
import { PWirkstoffDeleteDialogComponent } from './delete/p-wirkstoff-delete-dialog.component';
import { PWirkstoffRoutingModule } from './route/p-wirkstoff-routing.module';

@NgModule({
  imports: [SharedModule, PWirkstoffRoutingModule],
  declarations: [PWirkstoffComponent, PWirkstoffDetailComponent, PWirkstoffUpdateComponent, PWirkstoffDeleteDialogComponent],
  entryComponents: [PWirkstoffDeleteDialogComponent],
})
export class PWirkstoffModule {}
