import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Muster16Component } from '../list/muster-16.component';
import { Muster16DetailComponent } from '../detail/muster-16-detail.component';
import { Muster16UpdateComponent } from '../update/muster-16-update.component';
import { Muster16RoutingResolveService } from './muster-16-routing-resolve.service';

const muster16Route: Routes = [
  {
    path: '',
    component: Muster16Component,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Muster16DetailComponent,
    resolve: {
      muster16: Muster16RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Muster16UpdateComponent,
    resolve: {
      muster16: Muster16RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Muster16UpdateComponent,
    resolve: {
      muster16: Muster16RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(muster16Route)],
  exports: [RouterModule],
})
export class Muster16RoutingModule {}
