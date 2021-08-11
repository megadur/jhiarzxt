import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApotheke, getApothekeIdentifier } from '../apotheke.model';

export type EntityResponseType = HttpResponse<IApotheke>;
export type EntityArrayResponseType = HttpResponse<IApotheke[]>;

@Injectable({ providedIn: 'root' })
export class ApothekeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apothekes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(apotheke: IApotheke): Observable<EntityResponseType> {
    return this.http.post<IApotheke>(this.resourceUrl, apotheke, { observe: 'response' });
  }

  update(apotheke: IApotheke): Observable<EntityResponseType> {
    return this.http.put<IApotheke>(`${this.resourceUrl}/${getApothekeIdentifier(apotheke) as number}`, apotheke, { observe: 'response' });
  }

  partialUpdate(apotheke: IApotheke): Observable<EntityResponseType> {
    return this.http.patch<IApotheke>(`${this.resourceUrl}/${getApothekeIdentifier(apotheke) as number}`, apotheke, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApotheke>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApotheke[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addApothekeToCollectionIfMissing(apothekeCollection: IApotheke[], ...apothekesToCheck: (IApotheke | null | undefined)[]): IApotheke[] {
    const apothekes: IApotheke[] = apothekesToCheck.filter(isPresent);
    if (apothekes.length > 0) {
      const apothekeCollectionIdentifiers = apothekeCollection.map(apothekeItem => getApothekeIdentifier(apothekeItem)!);
      const apothekesToAdd = apothekes.filter(apothekeItem => {
        const apothekeIdentifier = getApothekeIdentifier(apothekeItem);
        if (apothekeIdentifier == null || apothekeCollectionIdentifiers.includes(apothekeIdentifier)) {
          return false;
        }
        apothekeCollectionIdentifiers.push(apothekeIdentifier);
        return true;
      });
      return [...apothekesToAdd, ...apothekeCollection];
    }
    return apothekeCollection;
  }
}
