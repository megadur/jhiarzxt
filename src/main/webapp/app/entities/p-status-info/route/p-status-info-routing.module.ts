import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PStatusInfoComponent } from '../list/p-status-info.component';
import { PStatusInfoDetailComponent } from '../detail/p-status-info-detail.component';
import { PStatusInfoUpdateComponent } from '../update/p-status-info-update.component';
import { PStatusInfoRoutingResolveService } from './p-status-info-routing-resolve.service';

const pStatusInfoRoute: Routes = [
  {
    path: '',
    component: PStatusInfoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PStatusInfoDetailComponent,
    resolve: {
      pStatusInfo: PStatusInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PStatusInfoUpdateComponent,
    resolve: {
      pStatusInfo: PStatusInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PStatusInfoUpdateComponent,
    resolve: {
      pStatusInfo: PStatusInfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pStatusInfoRoute)],
  exports: [RouterModule],
})
export class PStatusInfoRoutingModule {}
