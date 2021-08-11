import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PRezeptAbgComponent } from './list/p-rezept-abg.component';
import { PRezeptAbgDetailComponent } from './detail/p-rezept-abg-detail.component';
import { PRezeptAbgUpdateComponent } from './update/p-rezept-abg-update.component';
import { PRezeptAbgDeleteDialogComponent } from './delete/p-rezept-abg-delete-dialog.component';
import { PRezeptAbgRoutingModule } from './route/p-rezept-abg-routing.module';

@NgModule({
  imports: [SharedModule, PRezeptAbgRoutingModule],
  declarations: [PRezeptAbgComponent, PRezeptAbgDetailComponent, PRezeptAbgUpdateComponent, PRezeptAbgDeleteDialogComponent],
  entryComponents: [PRezeptAbgDeleteDialogComponent],
})
export class PRezeptAbgModule {}
