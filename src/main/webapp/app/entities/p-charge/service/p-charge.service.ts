import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPCharge, getPChargeIdentifier } from '../p-charge.model';

export type EntityResponseType = HttpResponse<IPCharge>;
export type EntityArrayResponseType = HttpResponse<IPCharge[]>;

@Injectable({ providedIn: 'root' })
export class PChargeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-charges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pCharge: IPCharge): Observable<EntityResponseType> {
    return this.http.post<IPCharge>(this.resourceUrl, pCharge, { observe: 'response' });
  }

  update(pCharge: IPCharge): Observable<EntityResponseType> {
    return this.http.put<IPCharge>(`${this.resourceUrl}/${getPChargeIdentifier(pCharge) as number}`, pCharge, { observe: 'response' });
  }

  partialUpdate(pCharge: IPCharge): Observable<EntityResponseType> {
    return this.http.patch<IPCharge>(`${this.resourceUrl}/${getPChargeIdentifier(pCharge) as number}`, pCharge, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPCharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPChargeToCollectionIfMissing(pChargeCollection: IPCharge[], ...pChargesToCheck: (IPCharge | null | undefined)[]): IPCharge[] {
    const pCharges: IPCharge[] = pChargesToCheck.filter(isPresent);
    if (pCharges.length > 0) {
      const pChargeCollectionIdentifiers = pChargeCollection.map(pChargeItem => getPChargeIdentifier(pChargeItem)!);
      const pChargesToAdd = pCharges.filter(pChargeItem => {
        const pChargeIdentifier = getPChargeIdentifier(pChargeItem);
        if (pChargeIdentifier == null || pChargeCollectionIdentifiers.includes(pChargeIdentifier)) {
          return false;
        }
        pChargeCollectionIdentifiers.push(pChargeIdentifier);
        return true;
      });
      return [...pChargesToAdd, ...pChargeCollection];
    }
    return pChargeCollection;
  }
}
