import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMuster16, getMuster16Identifier } from '../muster-16.model';

export type EntityResponseType = HttpResponse<IMuster16>;
export type EntityArrayResponseType = HttpResponse<IMuster16[]>;

@Injectable({ providedIn: 'root' })
export class Muster16Service {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/muster-16-s');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(muster16: IMuster16): Observable<EntityResponseType> {
    return this.http.post<IMuster16>(this.resourceUrl, muster16, { observe: 'response' });
  }

  update(muster16: IMuster16): Observable<EntityResponseType> {
    return this.http.put<IMuster16>(`${this.resourceUrl}/${getMuster16Identifier(muster16) as number}`, muster16, { observe: 'response' });
  }

  partialUpdate(muster16: IMuster16): Observable<EntityResponseType> {
    return this.http.patch<IMuster16>(`${this.resourceUrl}/${getMuster16Identifier(muster16) as number}`, muster16, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMuster16>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMuster16[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMuster16ToCollectionIfMissing(muster16Collection: IMuster16[], ...muster16sToCheck: (IMuster16 | null | undefined)[]): IMuster16[] {
    const muster16s: IMuster16[] = muster16sToCheck.filter(isPresent);
    if (muster16s.length > 0) {
      const muster16CollectionIdentifiers = muster16Collection.map(muster16Item => getMuster16Identifier(muster16Item)!);
      const muster16sToAdd = muster16s.filter(muster16Item => {
        const muster16Identifier = getMuster16Identifier(muster16Item);
        if (muster16Identifier == null || muster16CollectionIdentifiers.includes(muster16Identifier)) {
          return false;
        }
        muster16CollectionIdentifiers.push(muster16Identifier);
        return true;
      });
      return [...muster16sToAdd, ...muster16Collection];
    }
    return muster16Collection;
  }
}
