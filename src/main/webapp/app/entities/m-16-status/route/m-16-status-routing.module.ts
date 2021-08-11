import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { M16StatusComponent } from '../list/m-16-status.component';
import { M16StatusDetailComponent } from '../detail/m-16-status-detail.component';
import { M16StatusUpdateComponent } from '../update/m-16-status-update.component';
import { M16StatusRoutingResolveService } from './m-16-status-routing-resolve.service';

const m16StatusRoute: Routes = [
  {
    path: '',
    component: M16StatusComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: M16StatusDetailComponent,
    resolve: {
      m16Status: M16StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: M16StatusUpdateComponent,
    resolve: {
      m16Status: M16StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: M16StatusUpdateComponent,
    resolve: {
      m16Status: M16StatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(m16StatusRoute)],
  exports: [RouterModule],
})
export class M16StatusRoutingModule {}
