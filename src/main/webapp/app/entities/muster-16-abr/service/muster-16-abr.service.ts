import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMuster16Abr, getMuster16AbrIdentifier } from '../muster-16-abr.model';

export type EntityResponseType = HttpResponse<IMuster16Abr>;
export type EntityArrayResponseType = HttpResponse<IMuster16Abr[]>;

@Injectable({ providedIn: 'root' })
export class Muster16AbrService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/muster-16-abrs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(muster16Abr: IMuster16Abr): Observable<EntityResponseType> {
    return this.http.post<IMuster16Abr>(this.resourceUrl, muster16Abr, { observe: 'response' });
  }

  update(muster16Abr: IMuster16Abr): Observable<EntityResponseType> {
    return this.http.put<IMuster16Abr>(`${this.resourceUrl}/${getMuster16AbrIdentifier(muster16Abr) as number}`, muster16Abr, {
      observe: 'response',
    });
  }

  partialUpdate(muster16Abr: IMuster16Abr): Observable<EntityResponseType> {
    return this.http.patch<IMuster16Abr>(`${this.resourceUrl}/${getMuster16AbrIdentifier(muster16Abr) as number}`, muster16Abr, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMuster16Abr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMuster16Abr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMuster16AbrToCollectionIfMissing(
    muster16AbrCollection: IMuster16Abr[],
    ...muster16AbrsToCheck: (IMuster16Abr | null | undefined)[]
  ): IMuster16Abr[] {
    const muster16Abrs: IMuster16Abr[] = muster16AbrsToCheck.filter(isPresent);
    if (muster16Abrs.length > 0) {
      const muster16AbrCollectionIdentifiers = muster16AbrCollection.map(muster16AbrItem => getMuster16AbrIdentifier(muster16AbrItem)!);
      const muster16AbrsToAdd = muster16Abrs.filter(muster16AbrItem => {
        const muster16AbrIdentifier = getMuster16AbrIdentifier(muster16AbrItem);
        if (muster16AbrIdentifier == null || muster16AbrCollectionIdentifiers.includes(muster16AbrIdentifier)) {
          return false;
        }
        muster16AbrCollectionIdentifiers.push(muster16AbrIdentifier);
        return true;
      });
      return [...muster16AbrsToAdd, ...muster16AbrCollection];
    }
    return muster16AbrCollection;
  }
}
