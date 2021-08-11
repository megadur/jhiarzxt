import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPRezeptVer, getPRezeptVerIdentifier } from '../p-rezept-ver.model';

export type EntityResponseType = HttpResponse<IPRezeptVer>;
export type EntityArrayResponseType = HttpResponse<IPRezeptVer[]>;

@Injectable({ providedIn: 'root' })
export class PRezeptVerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-rezept-vers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pRezeptVer: IPRezeptVer): Observable<EntityResponseType> {
    return this.http.post<IPRezeptVer>(this.resourceUrl, pRezeptVer, { observe: 'response' });
  }

  update(pRezeptVer: IPRezeptVer): Observable<EntityResponseType> {
    return this.http.put<IPRezeptVer>(`${this.resourceUrl}/${getPRezeptVerIdentifier(pRezeptVer) as number}`, pRezeptVer, {
      observe: 'response',
    });
  }

  partialUpdate(pRezeptVer: IPRezeptVer): Observable<EntityResponseType> {
    return this.http.patch<IPRezeptVer>(`${this.resourceUrl}/${getPRezeptVerIdentifier(pRezeptVer) as number}`, pRezeptVer, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPRezeptVer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPRezeptVer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPRezeptVerToCollectionIfMissing(
    pRezeptVerCollection: IPRezeptVer[],
    ...pRezeptVersToCheck: (IPRezeptVer | null | undefined)[]
  ): IPRezeptVer[] {
    const pRezeptVers: IPRezeptVer[] = pRezeptVersToCheck.filter(isPresent);
    if (pRezeptVers.length > 0) {
      const pRezeptVerCollectionIdentifiers = pRezeptVerCollection.map(pRezeptVerItem => getPRezeptVerIdentifier(pRezeptVerItem)!);
      const pRezeptVersToAdd = pRezeptVers.filter(pRezeptVerItem => {
        const pRezeptVerIdentifier = getPRezeptVerIdentifier(pRezeptVerItem);
        if (pRezeptVerIdentifier == null || pRezeptVerCollectionIdentifiers.includes(pRezeptVerIdentifier)) {
          return false;
        }
        pRezeptVerCollectionIdentifiers.push(pRezeptVerIdentifier);
        return true;
      });
      return [...pRezeptVersToAdd, ...pRezeptVerCollection];
    }
    return pRezeptVerCollection;
  }
}
