import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApothekeComponent } from './list/apotheke.component';
import { ApothekeDetailComponent } from './detail/apotheke-detail.component';
import { ApothekeUpdateComponent } from './update/apotheke-update.component';
import { ApothekeDeleteDialogComponent } from './delete/apotheke-delete-dialog.component';
import { ApothekeRoutingModule } from './route/apotheke-routing.module';

@NgModule({
  imports: [SharedModule, ApothekeRoutingModule],
  declarations: [ApothekeComponent, ApothekeDetailComponent, ApothekeUpdateComponent, ApothekeDeleteDialogComponent],
  entryComponents: [ApothekeDeleteDialogComponent],
})
export class ApothekeModule {}
