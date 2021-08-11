import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Muster16AbgComponent } from './list/muster-16-abg.component';
import { Muster16AbgDetailComponent } from './detail/muster-16-abg-detail.component';
import { Muster16AbgUpdateComponent } from './update/muster-16-abg-update.component';
import { Muster16AbgDeleteDialogComponent } from './delete/muster-16-abg-delete-dialog.component';
import { Muster16AbgRoutingModule } from './route/muster-16-abg-routing.module';

@NgModule({
  imports: [SharedModule, Muster16AbgRoutingModule],
  declarations: [Muster16AbgComponent, Muster16AbgDetailComponent, Muster16AbgUpdateComponent, Muster16AbgDeleteDialogComponent],
  entryComponents: [Muster16AbgDeleteDialogComponent],
})
export class Muster16AbgModule {}
