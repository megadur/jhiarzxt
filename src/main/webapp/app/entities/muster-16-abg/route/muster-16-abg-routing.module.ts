import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Muster16AbgComponent } from '../list/muster-16-abg.component';
import { Muster16AbgDetailComponent } from '../detail/muster-16-abg-detail.component';
import { Muster16AbgUpdateComponent } from '../update/muster-16-abg-update.component';
import { Muster16AbgRoutingResolveService } from './muster-16-abg-routing-resolve.service';

const muster16AbgRoute: Routes = [
  {
    path: '',
    component: Muster16AbgComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Muster16AbgDetailComponent,
    resolve: {
      muster16Abg: Muster16AbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Muster16AbgUpdateComponent,
    resolve: {
      muster16Abg: Muster16AbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Muster16AbgUpdateComponent,
    resolve: {
      muster16Abg: Muster16AbgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(muster16AbgRoute)],
  exports: [RouterModule],
})
export class Muster16AbgRoutingModule {}
