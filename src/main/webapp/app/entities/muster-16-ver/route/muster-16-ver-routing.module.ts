import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Muster16VerComponent } from '../list/muster-16-ver.component';
import { Muster16VerDetailComponent } from '../detail/muster-16-ver-detail.component';
import { Muster16VerUpdateComponent } from '../update/muster-16-ver-update.component';
import { Muster16VerRoutingResolveService } from './muster-16-ver-routing-resolve.service';

const muster16VerRoute: Routes = [
  {
    path: '',
    component: Muster16VerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Muster16VerDetailComponent,
    resolve: {
      muster16Ver: Muster16VerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Muster16VerUpdateComponent,
    resolve: {
      muster16Ver: Muster16VerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Muster16VerUpdateComponent,
    resolve: {
      muster16Ver: Muster16VerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(muster16VerRoute)],
  exports: [RouterModule],
})
export class Muster16VerRoutingModule {}
