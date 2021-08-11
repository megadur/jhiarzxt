import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PRezeptComponent } from './list/p-rezept.component';
import { PRezeptDetailComponent } from './detail/p-rezept-detail.component';
import { PRezeptUpdateComponent } from './update/p-rezept-update.component';
import { PRezeptDeleteDialogComponent } from './delete/p-rezept-delete-dialog.component';
import { PRezeptRoutingModule } from './route/p-rezept-routing.module';

@NgModule({
  imports: [SharedModule, PRezeptRoutingModule],
  declarations: [PRezeptComponent, PRezeptDetailComponent, PRezeptUpdateComponent, PRezeptDeleteDialogComponent],
  entryComponents: [PRezeptDeleteDialogComponent],
})
export class PRezeptModule {}
