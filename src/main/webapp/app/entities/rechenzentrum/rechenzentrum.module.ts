import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RechenzentrumComponent } from './list/rechenzentrum.component';
import { RechenzentrumDetailComponent } from './detail/rechenzentrum-detail.component';
import { RechenzentrumUpdateComponent } from './update/rechenzentrum-update.component';
import { RechenzentrumDeleteDialogComponent } from './delete/rechenzentrum-delete-dialog.component';
import { RechenzentrumRoutingModule } from './route/rechenzentrum-routing.module';

@NgModule({
  imports: [SharedModule, RechenzentrumRoutingModule],
  declarations: [RechenzentrumComponent, RechenzentrumDetailComponent, RechenzentrumUpdateComponent, RechenzentrumDeleteDialogComponent],
  entryComponents: [RechenzentrumDeleteDialogComponent],
})
export class RechenzentrumModule {}
