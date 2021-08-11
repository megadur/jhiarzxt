import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IERezept, getERezeptIdentifier } from '../e-rezept.model';

export type EntityResponseType = HttpResponse<IERezept>;
export type EntityArrayResponseType = HttpResponse<IERezept[]>;

@Injectable({ providedIn: 'root' })
export class ERezeptService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/e-rezepts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eRezept: IERezept): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eRezept);
    return this.http
      .post<IERezept>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(eRezept: IERezept): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eRezept);
    return this.http
      .put<IERezept>(`${this.resourceUrl}/${getERezeptIdentifier(eRezept) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(eRezept: IERezept): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(eRezept);
    return this.http
      .patch<IERezept>(`${this.resourceUrl}/${getERezeptIdentifier(eRezept) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IERezept>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IERezept[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addERezeptToCollectionIfMissing(eRezeptCollection: IERezept[], ...eRezeptsToCheck: (IERezept | null | undefined)[]): IERezept[] {
    const eRezepts: IERezept[] = eRezeptsToCheck.filter(isPresent);
    if (eRezepts.length > 0) {
      const eRezeptCollectionIdentifiers = eRezeptCollection.map(eRezeptItem => getERezeptIdentifier(eRezeptItem)!);
      const eRezeptsToAdd = eRezepts.filter(eRezeptItem => {
        const eRezeptIdentifier = getERezeptIdentifier(eRezeptItem);
        if (eRezeptIdentifier == null || eRezeptCollectionIdentifiers.includes(eRezeptIdentifier)) {
          return false;
        }
        eRezeptCollectionIdentifiers.push(eRezeptIdentifier);
        return true;
      });
      return [...eRezeptsToAdd, ...eRezeptCollection];
    }
    return eRezeptCollection;
  }

  protected convertDateFromClient(eRezept: IERezept): IERezept {
    return Object.assign({}, eRezept, {
      abgDatum: eRezept.abgDatum?.isValid() ? eRezept.abgDatum.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.abgDatum = res.body.abgDatum ? dayjs(res.body.abgDatum) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((eRezept: IERezept) => {
        eRezept.abgDatum = eRezept.abgDatum ? dayjs(eRezept.abgDatum) : undefined;
      });
    }
    return res;
  }
}
