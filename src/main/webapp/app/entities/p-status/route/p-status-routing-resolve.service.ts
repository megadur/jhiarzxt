import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPStatus, PStatus } from '../p-status.model';
import { PStatusService } from '../service/p-status.service';

@Injectable({ providedIn: 'root' })
export class PStatusRoutingResolveService implements Resolve<IPStatus> {
  constructor(protected service: PStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pStatus: HttpResponse<PStatus>) => {
          if (pStatus.body) {
            return of(pStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PStatus());
  }
}
