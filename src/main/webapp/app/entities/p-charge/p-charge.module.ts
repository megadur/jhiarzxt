import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PChargeComponent } from './list/p-charge.component';
import { PChargeDetailComponent } from './detail/p-charge-detail.component';
import { PChargeUpdateComponent } from './update/p-charge-update.component';
import { PChargeDeleteDialogComponent } from './delete/p-charge-delete-dialog.component';
import { PChargeRoutingModule } from './route/p-charge-routing.module';

@NgModule({
  imports: [SharedModule, PChargeRoutingModule],
  declarations: [PChargeComponent, PChargeDetailComponent, PChargeUpdateComponent, PChargeDeleteDialogComponent],
  entryComponents: [PChargeDeleteDialogComponent],
})
export class PChargeModule {}
