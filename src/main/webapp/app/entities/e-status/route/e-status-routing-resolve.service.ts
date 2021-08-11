import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEStatus, EStatus } from '../e-status.model';
import { EStatusService } from '../service/e-status.service';

@Injectable({ providedIn: 'root' })
export class EStatusRoutingResolveService implements Resolve<IEStatus> {
  constructor(protected service: EStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eStatus: HttpResponse<EStatus>) => {
          if (eStatus.body) {
            return of(eStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EStatus());
  }
}
