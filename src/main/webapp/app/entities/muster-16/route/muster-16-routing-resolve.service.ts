import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMuster16, Muster16 } from '../muster-16.model';
import { Muster16Service } from '../service/muster-16.service';

@Injectable({ providedIn: 'root' })
export class Muster16RoutingResolveService implements Resolve<IMuster16> {
  constructor(protected service: Muster16Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMuster16> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((muster16: HttpResponse<Muster16>) => {
          if (muster16.body) {
            return of(muster16.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Muster16());
  }
}
