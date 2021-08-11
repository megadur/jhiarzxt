import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IM16Status, M16Status } from '../m-16-status.model';
import { M16StatusService } from '../service/m-16-status.service';

@Injectable({ providedIn: 'root' })
export class M16StatusRoutingResolveService implements Resolve<IM16Status> {
  constructor(protected service: M16StatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IM16Status> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((m16Status: HttpResponse<M16Status>) => {
          if (m16Status.body) {
            return of(m16Status.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new M16Status());
  }
}
