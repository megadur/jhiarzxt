import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPStatusInfo, PStatusInfo } from '../p-status-info.model';
import { PStatusInfoService } from '../service/p-status-info.service';

@Injectable({ providedIn: 'root' })
export class PStatusInfoRoutingResolveService implements Resolve<IPStatusInfo> {
  constructor(protected service: PStatusInfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPStatusInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pStatusInfo: HttpResponse<PStatusInfo>) => {
          if (pStatusInfo.body) {
            return of(pStatusInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PStatusInfo());
  }
}
