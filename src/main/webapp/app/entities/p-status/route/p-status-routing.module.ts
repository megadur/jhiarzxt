import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PStatusComponent } from '../list/p-status.component';
import { PStatusDetailComponent } from '../detail/p-status-detail.component';
import { PStatusUpdateComponent } from '../update/p-status-update.component';
import { PStatusRoutingResolveService } from './p-status-routing-resolve.service';

const pStatusRoute: Routes = [
  {
    path: '',
    component: PStatusComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PStatusDetailComponent,
    resolve: {
      pStatus: PStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PStatusUpdateComponent,
    resolve: {
      pStatus: PStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PStatusUpdateComponent,
    resolve: {
      pStatus: PStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pStatusRoute)],
  exports: [RouterModule],
})
export class PStatusRoutingModule {}
