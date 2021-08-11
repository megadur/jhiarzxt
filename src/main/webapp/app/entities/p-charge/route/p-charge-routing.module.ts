import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PChargeComponent } from '../list/p-charge.component';
import { PChargeDetailComponent } from '../detail/p-charge-detail.component';
import { PChargeUpdateComponent } from '../update/p-charge-update.component';
import { PChargeRoutingResolveService } from './p-charge-routing-resolve.service';

const pChargeRoute: Routes = [
  {
    path: '',
    component: PChargeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PChargeDetailComponent,
    resolve: {
      pCharge: PChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PChargeUpdateComponent,
    resolve: {
      pCharge: PChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PChargeUpdateComponent,
    resolve: {
      pCharge: PChargeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pChargeRoute)],
  exports: [RouterModule],
})
export class PChargeRoutingModule {}
