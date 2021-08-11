import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PRezeptVerComponent } from './list/p-rezept-ver.component';
import { PRezeptVerDetailComponent } from './detail/p-rezept-ver-detail.component';
import { PRezeptVerUpdateComponent } from './update/p-rezept-ver-update.component';
import { PRezeptVerDeleteDialogComponent } from './delete/p-rezept-ver-delete-dialog.component';
import { PRezeptVerRoutingModule } from './route/p-rezept-ver-routing.module';

@NgModule({
  imports: [SharedModule, PRezeptVerRoutingModule],
  declarations: [PRezeptVerComponent, PRezeptVerDetailComponent, PRezeptVerUpdateComponent, PRezeptVerDeleteDialogComponent],
  entryComponents: [PRezeptVerDeleteDialogComponent],
})
export class PRezeptVerModule {}
