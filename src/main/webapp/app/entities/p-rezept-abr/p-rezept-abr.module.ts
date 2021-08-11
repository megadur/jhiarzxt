import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PRezeptAbrComponent } from './list/p-rezept-abr.component';
import { PRezeptAbrDetailComponent } from './detail/p-rezept-abr-detail.component';
import { PRezeptAbrUpdateComponent } from './update/p-rezept-abr-update.component';
import { PRezeptAbrDeleteDialogComponent } from './delete/p-rezept-abr-delete-dialog.component';
import { PRezeptAbrRoutingModule } from './route/p-rezept-abr-routing.module';

@NgModule({
  imports: [SharedModule, PRezeptAbrRoutingModule],
  declarations: [PRezeptAbrComponent, PRezeptAbrDetailComponent, PRezeptAbrUpdateComponent, PRezeptAbrDeleteDialogComponent],
  entryComponents: [PRezeptAbrDeleteDialogComponent],
})
export class PRezeptAbrModule {}
