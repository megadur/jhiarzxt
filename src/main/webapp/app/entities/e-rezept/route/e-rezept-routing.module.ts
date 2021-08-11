import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ERezeptComponent } from '../list/e-rezept.component';
import { ERezeptDetailComponent } from '../detail/e-rezept-detail.component';
import { ERezeptUpdateComponent } from '../update/e-rezept-update.component';
import { ERezeptRoutingResolveService } from './e-rezept-routing-resolve.service';

const eRezeptRoute: Routes = [
  {
    path: '',
    component: ERezeptComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ERezeptDetailComponent,
    resolve: {
      eRezept: ERezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ERezeptUpdateComponent,
    resolve: {
      eRezept: ERezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ERezeptUpdateComponent,
    resolve: {
      eRezept: ERezeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eRezeptRoute)],
  exports: [RouterModule],
})
export class ERezeptRoutingModule {}
