import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ERezeptComponent } from './list/e-rezept.component';
import { ERezeptDetailComponent } from './detail/e-rezept-detail.component';
import { ERezeptUpdateComponent } from './update/e-rezept-update.component';
import { ERezeptDeleteDialogComponent } from './delete/e-rezept-delete-dialog.component';
import { ERezeptRoutingModule } from './route/e-rezept-routing.module';

@NgModule({
  imports: [SharedModule, ERezeptRoutingModule],
  declarations: [ERezeptComponent, ERezeptDetailComponent, ERezeptUpdateComponent, ERezeptDeleteDialogComponent],
  entryComponents: [ERezeptDeleteDialogComponent],
})
export class ERezeptModule {}
