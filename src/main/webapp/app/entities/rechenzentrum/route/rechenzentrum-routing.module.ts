import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RechenzentrumComponent } from '../list/rechenzentrum.component';
import { RechenzentrumDetailComponent } from '../detail/rechenzentrum-detail.component';
import { RechenzentrumUpdateComponent } from '../update/rechenzentrum-update.component';
import { RechenzentrumRoutingResolveService } from './rechenzentrum-routing-resolve.service';

const rechenzentrumRoute: Routes = [
  {
    path: '',
    component: RechenzentrumComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RechenzentrumDetailComponent,
    resolve: {
      rechenzentrum: RechenzentrumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RechenzentrumUpdateComponent,
    resolve: {
      rechenzentrum: RechenzentrumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RechenzentrumUpdateComponent,
    resolve: {
      rechenzentrum: RechenzentrumRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rechenzentrumRoute)],
  exports: [RouterModule],
})
export class RechenzentrumRoutingModule {}
