import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { Muster16Component } from './list/muster-16.component';
import { Muster16DetailComponent } from './detail/muster-16-detail.component';
import { Muster16UpdateComponent } from './update/muster-16-update.component';
import { Muster16DeleteDialogComponent } from './delete/muster-16-delete-dialog.component';
import { Muster16RoutingModule } from './route/muster-16-routing.module';

@NgModule({
  imports: [SharedModule, Muster16RoutingModule],
  declarations: [Muster16Component, Muster16DetailComponent, Muster16UpdateComponent, Muster16DeleteDialogComponent],
  entryComponents: [Muster16DeleteDialogComponent],
})
export class Muster16Module {}
