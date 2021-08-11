import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEAenderung, EAenderung } from '../e-aenderung.model';
import { EAenderungService } from '../service/e-aenderung.service';

@Injectable({ providedIn: 'root' })
export class EAenderungRoutingResolveService implements Resolve<IEAenderung> {
  constructor(protected service: EAenderungService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEAenderung> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eAenderung: HttpResponse<EAenderung>) => {
          if (eAenderung.body) {
            return of(eAenderung.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EAenderung());
  }
}
