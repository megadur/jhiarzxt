import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRechenzentrum, getRechenzentrumIdentifier } from '../rechenzentrum.model';

export type EntityResponseType = HttpResponse<IRechenzentrum>;
export type EntityArrayResponseType = HttpResponse<IRechenzentrum[]>;

@Injectable({ providedIn: 'root' })
export class RechenzentrumService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rechenzentrums');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rechenzentrum: IRechenzentrum): Observable<EntityResponseType> {
    return this.http.post<IRechenzentrum>(this.resourceUrl, rechenzentrum, { observe: 'response' });
  }

  update(rechenzentrum: IRechenzentrum): Observable<EntityResponseType> {
    return this.http.put<IRechenzentrum>(`${this.resourceUrl}/${getRechenzentrumIdentifier(rechenzentrum) as number}`, rechenzentrum, {
      observe: 'response',
    });
  }

  partialUpdate(rechenzentrum: IRechenzentrum): Observable<EntityResponseType> {
    return this.http.patch<IRechenzentrum>(`${this.resourceUrl}/${getRechenzentrumIdentifier(rechenzentrum) as number}`, rechenzentrum, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRechenzentrum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRechenzentrum[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRechenzentrumToCollectionIfMissing(
    rechenzentrumCollection: IRechenzentrum[],
    ...rechenzentrumsToCheck: (IRechenzentrum | null | undefined)[]
  ): IRechenzentrum[] {
    const rechenzentrums: IRechenzentrum[] = rechenzentrumsToCheck.filter(isPresent);
    if (rechenzentrums.length > 0) {
      const rechenzentrumCollectionIdentifiers = rechenzentrumCollection.map(
        rechenzentrumItem => getRechenzentrumIdentifier(rechenzentrumItem)!
      );
      const rechenzentrumsToAdd = rechenzentrums.filter(rechenzentrumItem => {
        const rechenzentrumIdentifier = getRechenzentrumIdentifier(rechenzentrumItem);
        if (rechenzentrumIdentifier == null || rechenzentrumCollectionIdentifiers.includes(rechenzentrumIdentifier)) {
          return false;
        }
        rechenzentrumCollectionIdentifiers.push(rechenzentrumIdentifier);
        return true;
      });
      return [...rechenzentrumsToAdd, ...rechenzentrumCollection];
    }
    return rechenzentrumCollection;
  }
}
