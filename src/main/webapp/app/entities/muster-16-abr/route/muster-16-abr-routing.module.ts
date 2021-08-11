import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Muster16AbrComponent } from '../list/muster-16-abr.component';
import { Muster16AbrDetailComponent } from '../detail/muster-16-abr-detail.component';
import { Muster16AbrUpdateComponent } from '../update/muster-16-abr-update.component';
import { Muster16AbrRoutingResolveService } from './muster-16-abr-routing-resolve.service';

const muster16AbrRoute: Routes = [
  {
    path: '',
    component: Muster16AbrComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Muster16AbrDetailComponent,
    resolve: {
      muster16Abr: Muster16AbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Muster16AbrUpdateComponent,
    resolve: {
      muster16Abr: Muster16AbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Muster16AbrUpdateComponent,
    resolve: {
      muster16Abr: Muster16AbrRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(muster16AbrRoute)],
  exports: [RouterModule],
})
export class Muster16AbrRoutingModule {}
