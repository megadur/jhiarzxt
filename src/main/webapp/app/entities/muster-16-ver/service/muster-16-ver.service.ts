import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMuster16Ver, getMuster16VerIdentifier } from '../muster-16-ver.model';

export type EntityResponseType = HttpResponse<IMuster16Ver>;
export type EntityArrayResponseType = HttpResponse<IMuster16Ver[]>;

@Injectable({ providedIn: 'root' })
export class Muster16VerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/muster-16-vers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(muster16Ver: IMuster16Ver): Observable<EntityResponseType> {
    return this.http.post<IMuster16Ver>(this.resourceUrl, muster16Ver, { observe: 'response' });
  }

  update(muster16Ver: IMuster16Ver): Observable<EntityResponseType> {
    return this.http.put<IMuster16Ver>(`${this.resourceUrl}/${getMuster16VerIdentifier(muster16Ver) as number}`, muster16Ver, {
      observe: 'response',
    });
  }

  partialUpdate(muster16Ver: IMuster16Ver): Observable<EntityResponseType> {
    return this.http.patch<IMuster16Ver>(`${this.resourceUrl}/${getMuster16VerIdentifier(muster16Ver) as number}`, muster16Ver, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMuster16Ver>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMuster16Ver[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMuster16VerToCollectionIfMissing(
    muster16VerCollection: IMuster16Ver[],
    ...muster16VersToCheck: (IMuster16Ver | null | undefined)[]
  ): IMuster16Ver[] {
    const muster16Vers: IMuster16Ver[] = muster16VersToCheck.filter(isPresent);
    if (muster16Vers.length > 0) {
      const muster16VerCollectionIdentifiers = muster16VerCollection.map(muster16VerItem => getMuster16VerIdentifier(muster16VerItem)!);
      const muster16VersToAdd = muster16Vers.filter(muster16VerItem => {
        const muster16VerIdentifier = getMuster16VerIdentifier(muster16VerItem);
        if (muster16VerIdentifier == null || muster16VerCollectionIdentifiers.includes(muster16VerIdentifier)) {
          return false;
        }
        muster16VerCollectionIdentifiers.push(muster16VerIdentifier);
        return true;
      });
      return [...muster16VersToAdd, ...muster16VerCollection];
    }
    return muster16VerCollection;
  }
}
