import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPCharge, PCharge } from '../p-charge.model';

import { PChargeService } from './p-charge.service';

describe('Service Tests', () => {
  describe('PCharge Service', () => {
    let service: PChargeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPCharge;
    let expectedResult: IPCharge | IPCharge[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PChargeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pChargeId: 'AAAAAAA',
        pRezeptId: 'AAAAAAA',
        anzahlApplikationen: 'AAAAAAA',
        apoIk: 'AAAAAAA',
        chargenNr: 'AAAAAAA',
        herstellerNr: 'AAAAAAA',
        herstellerSchluessel: 'AAAAAAA',
        herstellungsDatum: 'AAAAAAA',
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

      it('should create a PCharge', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PCharge()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PCharge', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pChargeId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            anzahlApplikationen: 'BBBBBB',
            apoIk: 'BBBBBB',
            chargenNr: 'BBBBBB',
            herstellerNr: 'BBBBBB',
            herstellerSchluessel: 'BBBBBB',
            herstellungsDatum: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PCharge', () => {
        const patchObject = Object.assign(
          {
            pChargeId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            anzahlApplikationen: 'BBBBBB',
            herstellungsDatum: 'BBBBBB',
          },
          new PCharge()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PCharge', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pChargeId: 'BBBBBB',
            pRezeptId: 'BBBBBB',
            anzahlApplikationen: 'BBBBBB',
            apoIk: 'BBBBBB',
            chargenNr: 'BBBBBB',
            herstellerNr: 'BBBBBB',
            herstellerSchluessel: 'BBBBBB',
            herstellungsDatum: 'BBBBBB',
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

      it('should delete a PCharge', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPChargeToCollectionIfMissing', () => {
        it('should add a PCharge to an empty array', () => {
          const pCharge: IPCharge = { id: 123 };
          expectedResult = service.addPChargeToCollectionIfMissing([], pCharge);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pCharge);
        });

        it('should not add a PCharge to an array that contains it', () => {
          const pCharge: IPCharge = { id: 123 };
          const pChargeCollection: IPCharge[] = [
            {
              ...pCharge,
            },
            { id: 456 },
          ];
          expectedResult = service.addPChargeToCollectionIfMissing(pChargeCollection, pCharge);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PCharge to an array that doesn't contain it", () => {
          const pCharge: IPCharge = { id: 123 };
          const pChargeCollection: IPCharge[] = [{ id: 456 }];
          expectedResult = service.addPChargeToCollectionIfMissing(pChargeCollection, pCharge);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pCharge);
        });

        it('should add only unique PCharge to an array', () => {
          const pChargeArray: IPCharge[] = [{ id: 123 }, { id: 456 }, { id: 39911 }];
          const pChargeCollection: IPCharge[] = [{ id: 123 }];
          expectedResult = service.addPChargeToCollectionIfMissing(pChargeCollection, ...pChargeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pCharge: IPCharge = { id: 123 };
          const pCharge2: IPCharge = { id: 456 };
          expectedResult = service.addPChargeToCollectionIfMissing([], pCharge, pCharge2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pCharge);
          expect(expectedResult).toContain(pCharge2);
        });

        it('should accept null and undefined values', () => {
          const pCharge: IPCharge = { id: 123 };
          expectedResult = service.addPChargeToCollectionIfMissing([], null, pCharge, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pCharge);
        });

        it('should return initial array if no PCharge is added', () => {
          const pChargeCollection: IPCharge[] = [{ id: 123 }];
          expectedResult = service.addPChargeToCollectionIfMissing(pChargeCollection, undefined, null);
          expect(expectedResult).toEqual(pChargeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
