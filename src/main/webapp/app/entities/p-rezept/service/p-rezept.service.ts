import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPRezept, getPRezeptIdentifier } from '../p-rezept.model';

export type EntityResponseType = HttpResponse<IPRezept>;
export type EntityArrayResponseType = HttpResponse<IPRezept[]>;

@Injectable({ providedIn: 'root' })
export class PRezeptService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/p-rezepts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pRezept: IPRezept): Observable<EntityResponseType> {
    return this.http.post<IPRezept>(this.resourceUrl, pRezept, { observe: 'response' });
  }

  update(pRezept: IPRezept): Observable<EntityResponseType> {
    return this.http.put<IPRezept>(`${this.resourceUrl}/${getPRezeptIdentifier(pRezept) as number}`, pRezept, { observe: 'response' });
  }

  partialUpdate(pRezept: IPRezept): Observable<EntityResponseType> {
    return this.http.patch<IPRezept>(`${this.resourceUrl}/${getPRezeptIdentifier(pRezept) as number}`, pRezept, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPRezept>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPRezept[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPRezeptToCollectionIfMissing(pRezeptCollection: IPRezept[], ...pRezeptsToCheck: (IPRezept | null | undefined)[]): IPRezept[] {
    const pRezepts: IPRezept[] = pRezeptsToCheck.filter(isPresent);
    if (pRezepts.length > 0) {
      const pRezeptCollectionIdentifiers = pRezeptCollection.map(pRezeptItem => getPRezeptIdentifier(pRezeptItem)!);
      const pRezeptsToAdd = pRezepts.filter(pRezeptItem => {
        const pRezeptIdentifier = getPRezeptIdentifier(pRezeptItem);
        if (pRezeptIdentifier == null || pRezeptCollectionIdentifiers.includes(pRezeptIdentifier)) {
          return false;
        }
        pRezeptCollectionIdentifiers.push(pRezeptIdentifier);
        return true;
      });
      return [...pRezeptsToAdd, ...pRezeptCollection];
    }
    return pRezeptCollection;
  }
}
