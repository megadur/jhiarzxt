import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPCharge, PCharge } from '../p-charge.model';
import { PChargeService } from '../service/p-charge.service';

@Injectable({ providedIn: 'root' })
export class PChargeRoutingResolveService implements Resolve<IPCharge> {
  constructor(protected service: PChargeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPCharge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pCharge: HttpResponse<PCharge>) => {
          if (pCharge.body) {
            return of(pCharge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PCharge());
  }
}
