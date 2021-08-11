import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRechenzentrum, Rechenzentrum } from '../rechenzentrum.model';
import { RechenzentrumService } from '../service/rechenzentrum.service';

@Injectable({ providedIn: 'root' })
export class RechenzentrumRoutingResolveService implements Resolve<IRechenzentrum> {
  constructor(protected service: RechenzentrumService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRechenzentrum> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rechenzentrum: HttpResponse<Rechenzentrum>) => {
          if (rechenzentrum.body) {
            return of(rechenzentrum.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rechenzentrum());
  }
}
