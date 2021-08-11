import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPWirkstoff, PWirkstoff } from '../p-wirkstoff.model';

import { PWirkstoffService } from './p-wirkstoff.service';

describe('Service Tests', () => {
  describe('PWirkstoff Service', () => {
    let service: PWirkstoffService;
    let httpMock: HttpTestingController;
    let elemDefault: IPWirkstoff;
    let expectedResult: IPWirkstoff | IPWirkstoff[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PWirkstoffService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pWirkstoffId: 'AAAAAAA',
        pChargeId: 'AAAAAAA',
        apoIk: 'AAAAAAA',
        faktor: 'AAAAAAA',
        faktorKennzeichen: 'AAAAAAA',
        notiz: 'AAAAAAA',
        pPosNr: 'AAAAAAA',
        preisKennzeichen: 'AAAAAAA',
        pzn: 'AAAAAAA',
        taxe: 'AAAAAAA',
        wirkstoffName: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PWirkstoff', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PWirkstoff()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PWirkstoff', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pWirkstoffId: 'BBBBBB',
            pChargeId: 'BBBBBB',
            apoIk: 'BBBBBB',
            faktor: 'BBBBBB',
            faktorKennzeichen: 'BBBBBB',
            notiz: 'BBBBBB',
            pPosNr: 'BBBBBB',
            preisKennzeichen: 'BBBBBB',
            pzn: 'BBBBBB',
            taxe: 'BBBBBB',
            wirkstoffName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PWirkstoff', () => {
        const patchObject = Object.assign(
          {
            pChargeId: 'BBBBBB',
            apoIk: 'BBBBBB',
            faktor: 'BBBBBB',
            faktorKennzeichen: 'BBBBBB',
            pPosNr: 'BBBBBB',
            preisKennzeichen: 'BBBBBB',
          },
          new PWirkstoff()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PWirkstoff', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pWirkstoffId: 'BBBBBB',
            pChargeId: 'BBBBBB',
            apoIk: 'BBBBBB',
            faktor: 'BBBBBB',
            faktorKennzeichen: 'BBBBBB',
            notiz: 'BBBBBB',
            pPosNr: 'BBBBBB',
            preisKennzeichen: 'BBBBBB',
            pzn: 'BBBBBB',
            taxe: 'BBBBBB',
            wirkstoffName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PWirkstoff', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPWirkstoffToCollectionIfMissing', () => {
        it('should add a PWirkstoff to an empty array', () => {
          const pWirkstoff: IPWirkstoff = { id: 123 };
          expectedResult = service.addPWirkstoffToCollectionIfMissing([], pWirkstoff);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pWirkstoff);
        });

        it('should not add a PWirkstoff to an array that contains it', () => {
          const pWirkstoff: IPWirkstoff = { id: 123 };
          const pWirkstoffCollection: IPWirkstoff[] = [
            {
              ...pWirkstoff,
            },
            { id: 456 },
          ];
          expectedResult = service.addPWirkstoffToCollectionIfMissing(pWirkstoffCollection, pWirkstoff);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PWirkstoff to an array that doesn't contain it", () => {
          const pWirkstoff: IPWirkstoff = { id: 123 };
          const pWirkstoffCollection: IPWirkstoff[] = [{ id: 456 }];
          expectedResult = service.addPWirkstoffToCollectionIfMissing(pWirkstoffCollection, pWirkstoff);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pWirkstoff);
        });

        it('should add only unique PWirkstoff to an array', () => {
          const pWirkstoffArray: IPWirkstoff[] = [{ id: 123 }, { id: 456 }, { id: 74464 }];
          const pWirkstoffCollection: IPWirkstoff[] = [{ id: 123 }];
          expectedResult = service.addPWirkstoffToCollectionIfMissing(pWirkstoffCollection, ...pWirkstoffArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pWirkstoff: IPWirkstoff = { id: 123 };
          const pWirkstoff2: IPWirkstoff = { id: 456 };
          expectedResult = service.addPWirkstoffToCollectionIfMissing([], pWirkstoff, pWirkstoff2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pWirkstoff);
          expect(expectedResult).toContain(pWirkstoff2);
        });

        it('should accept null and undefined values', () => {
          const pWirkstoff: IPWirkstoff = { id: 123 };
          expectedResult = service.addPWirkstoffToCollectionIfMissing([], null, pWirkstoff, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pWirkstoff);
        });

        it('should return initial array if no PWirkstoff is added', () => {
          const pWirkstoffCollection: IPWirkstoff[] = [{ id: 123 }];
          expectedResult = service.addPWirkstoffToCollectionIfMissing(pWirkstoffCollection, undefined, null);
          expect(expectedResult).toEqual(pWirkstoffCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
