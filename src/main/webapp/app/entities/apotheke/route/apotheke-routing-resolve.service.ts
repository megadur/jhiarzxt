import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApotheke, Apotheke } from '../apotheke.model';
import { ApothekeService } from '../service/apotheke.service';

@Injectable({ providedIn: 'root' })
export class ApothekeRoutingResolveService implements Resolve<IApotheke> {
  constructor(protected service: ApothekeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApotheke> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((apotheke: HttpResponse<Apotheke>) => {
          if (apotheke.body) {
            return of(apotheke.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Apotheke());
  }
}
