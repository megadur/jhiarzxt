import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPWirkstoff, PWirkstoff } from '../p-wirkstoff.model';
import { PWirkstoffService } from '../service/p-wirkstoff.service';

@Injectable({ providedIn: 'root' })
export class PWirkstoffRoutingResolveService implements Resolve<IPWirkstoff> {
  constructor(protected service: PWirkstoffService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPWirkstoff> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pWirkstoff: HttpResponse<PWirkstoff>) => {
          if (pWirkstoff.body) {
            return of(pWirkstoff.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PWirkstoff());
  }
}
