import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PWirkstoffComponent } from '../list/p-wirkstoff.component';
import { PWirkstoffDetailComponent } from '../detail/p-wirkstoff-detail.component';
import { PWirkstoffUpdateComponent } from '../update/p-wirkstoff-update.component';
import { PWirkstoffRoutingResolveService } from './p-wirkstoff-routing-resolve.service';

const pWirkstoffRoute: Routes = [
  {
    path: '',
    component: PWirkstoffComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PWirkstoffDetailComponent,
    resolve: {
      pWirkstoff: PWirkstoffRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PWirkstoffUpdateComponent,
    resolve: {
      pWirkstoff: PWirkstoffRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PWirkstoffUpdateComponent,
    resolve: {
      pWirkstoff: PWirkstoffRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pWirkstoffRoute)],
  exports: [RouterModule],
})
export class PWirkstoffRoutingModule {}
