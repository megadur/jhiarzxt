import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Muster16AbrComponent } from './list/muster-16-abr.component';
import { Muster16AbrDetailComponent } from './detail/muster-16-abr-detail.component';
import { Muster16AbrUpdateComponent } from './update/muster-16-abr-update.component';
import { Muster16AbrDeleteDialogComponent } from './delete/muster-16-abr-delete-dialog.component';
import { Muster16AbrRoutingModule } from './route/muster-16-abr-routing.module';

@NgModule({
  imports: [SharedModule, Muster16AbrRoutingModule],
  declarations: [Muster16AbrComponent, Muster16AbrDetailComponent, Muster16AbrUpdateComponent, Muster16AbrDeleteDialogComponent],
  entryComponents: [Muster16AbrDeleteDialogComponent],
})
export class Muster16AbrModule {}
