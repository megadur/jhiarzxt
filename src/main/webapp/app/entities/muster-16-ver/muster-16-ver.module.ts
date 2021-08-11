import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Muster16VerComponent } from './list/muster-16-ver.component';
import { Muster16VerDetailComponent } from './detail/muster-16-ver-detail.component';
import { Muster16VerUpdateComponent } from './update/muster-16-ver-update.component';
import { Muster16VerDeleteDialogComponent } from './delete/muster-16-ver-delete-dialog.component';
import { Muster16VerRoutingModule } from './route/muster-16-ver-routing.module';

@NgModule({
  imports: [SharedModule, Muster16VerRoutingModule],
  declarations: [Muster16VerComponent, Muster16VerDetailComponent, Muster16VerUpdateComponent, Muster16VerDeleteDialogComponent],
  entryComponents: [Muster16VerDeleteDialogComponent],
})
export class Muster16VerModule {}
