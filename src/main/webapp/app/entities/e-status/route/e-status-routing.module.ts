import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EStatusComponent } from '../list/e-status.component';
import { EStatusDetailComponent } from '../detail/e-status-detail.component';
import { EStatusUpdateComponent } from '../update/e-status-update.component';
import { EStatusRoutingResolveService } from './e-status-routing-resolve.service';

const eStatusRoute: Routes = [
  {
    path: '',
    component: EStatusComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EStatusDetailComponent,
    resolve: {
      eStatus: EStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EStatusUpdateComponent,
    resolve: {
      eStatus: EStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EStatusUpdateComponent,
    resolve: {
      eStatus: EStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eStatusRoute)],
  exports: [RouterModule],
})
export class EStatusRoutingModule {}
