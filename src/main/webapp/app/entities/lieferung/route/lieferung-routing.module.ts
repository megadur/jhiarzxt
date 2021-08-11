import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LieferungComponent } from '../list/lieferung.component';
import { LieferungDetailComponent } from '../detail/lieferung-detail.component';
import { LieferungUpdateComponent } from '../update/lieferung-update.component';
import { LieferungRoutingResolveService } from './lieferung-routing-resolve.service';

const lieferungRoute: Routes = [
  {
    path: '',
    component: LieferungComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LieferungDetailComponent,
    resolve: {
      lieferung: LieferungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LieferungUpdateComponent,
    resolve: {
      lieferung: LieferungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LieferungUpdateComponent,
    resolve: {
      lieferung: LieferungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lieferungRoute)],
  exports: [RouterModule],
})
export class LieferungRoutingModule {}
