import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMuster16Abg, getMuster16AbgIdentifier } from '../muster-16-abg.model';

export type EntityResponseType = HttpResponse<IMuster16Abg>;
export type EntityArrayResponseType = HttpResponse<IMuster16Abg[]>;

@Injectable({ providedIn: 'root' })
export class Muster16AbgService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/muster-16-abgs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(muster16Abg: IMuster16Abg): Observable<EntityResponseType> {
    return this.http.post<IMuster16Abg>(this.resourceUrl, muster16Abg, { observe: 'response' });
  }

  update(muster16Abg: IMuster16Abg): Observable<EntityResponseType> {
    return this.http.put<IMuster16Abg>(`${this.resourceUrl}/${getMuster16AbgIdentifier(muster16Abg) as number}`, muster16Abg, {
      observe: 'response',
    });
  }

  partialUpdate(muster16Abg: IMuster16Abg): Observable<EntityResponseType> {
    return this.http.patch<IMuster16Abg>(`${this.resourceUrl}/${getMuster16AbgIdentifier(muster16Abg) as number}`, muster16Abg, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMuster16Abg>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMuster16Abg[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMuster16AbgToCollectionIfMissing(
    muster16AbgCollection: IMuster16Abg[],
    ...muster16AbgsToCheck: (IMuster16Abg | null | undefined)[]
  ): IMuster16Abg[] {
    const muster16Abgs: IMuster16Abg[] = muster16AbgsToCheck.filter(isPresent);
    if (muster16Abgs.length > 0) {
      const muster16AbgCollectionIdentifiers = muster16AbgCollection.map(muster16AbgItem => getMuster16AbgIdentifier(muster16AbgItem)!);
      const muster16AbgsToAdd = muster16Abgs.filter(muster16AbgItem => {
        const muster16AbgIdentifier = getMuster16AbgIdentifier(muster16AbgItem);
        if (muster16AbgIdentifier == null || muster16AbgCollectionIdentifiers.includes(muster16AbgIdentifier)) {
          return false;
        }
        muster16AbgCollectionIdentifiers.push(muster16AbgIdentifier);
        return true;
      });
      return [...muster16AbgsToAdd, ...muster16AbgCollection];
    }
    return muster16AbgCollection;
  }
}
