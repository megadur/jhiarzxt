import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMuster16Abg, Muster16Abg } from '../muster-16-abg.model';

import { Muster16AbgService } from './muster-16-abg.service';

describe('Service Tests', () => {
  describe('Muster16Abg Service', () => {
    let service: Muster16AbgService;
    let httpMock: HttpTestingController;
    let elemDefault: IMuster16Abg;
    let expectedResult: IMuster16Abg | IMuster16Abg[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Muster16AbgService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        apoIk: 'AAAAAAA',
        lieferDat: 'AAAAAAA',
        aPeriode: 'AAAAAAA',
        arbPlatz: 'AAAAAAA',
        avsId: 'AAAAAAA',
        bediener: 'AAAAAAA',
        zuzahlung: 'AAAAAAA',
        gesBrutto: 'AAAAAAA',
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

      it('should create a Muster16Abg', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Muster16Abg()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Muster16Abg', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            apoIk: 'BBBBBB',
            lieferDat: 'BBBBBB',
            aPeriode: 'BBBBBB',
            arbPlatz: 'BBBBBB',
            avsId: 'BBBBBB',
            bediener: 'BBBBBB',
            zuzahlung: 'BBBBBB',
            gesBrutto: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Muster16Abg', () => {
        const patchObject = Object.assign(
          {
            apoIk: 'BBBBBB',
            lieferDat: 'BBBBBB',
            aPeriode: 'BBBBBB',
            bediener: 'BBBBBB',
          },
          new Muster16Abg()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Muster16Abg', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            apoIk: 'BBBBBB',
            lieferDat: 'BBBBBB',
            aPeriode: 'BBBBBB',
            arbPlatz: 'BBBBBB',
            avsId: 'BBBBBB',
            bediener: 'BBBBBB',
            zuzahlung: 'BBBBBB',
            gesBrutto: 'BBBBBB',
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

      it('should delete a Muster16Abg', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMuster16AbgToCollectionIfMissing', () => {
        it('should add a Muster16Abg to an empty array', () => {
          const muster16Abg: IMuster16Abg = { id: 123 };
          expectedResult = service.addMuster16AbgToCollectionIfMissing([], muster16Abg);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Abg);
        });

        it('should not add a Muster16Abg to an array that contains it', () => {
          const muster16Abg: IMuster16Abg = { id: 123 };
          const muster16AbgCollection: IMuster16Abg[] = [
            {
              ...muster16Abg,
            },
            { id: 456 },
          ];
          expectedResult = service.addMuster16AbgToCollectionIfMissing(muster16AbgCollection, muster16Abg);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Muster16Abg to an array that doesn't contain it", () => {
          const muster16Abg: IMuster16Abg = { id: 123 };
          const muster16AbgCollection: IMuster16Abg[] = [{ id: 456 }];
          expectedResult = service.addMuster16AbgToCollectionIfMissing(muster16AbgCollection, muster16Abg);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Abg);
        });

        it('should add only unique Muster16Abg to an array', () => {
          const muster16AbgArray: IMuster16Abg[] = [{ id: 123 }, { id: 456 }, { id: 66439 }];
          const muster16AbgCollection: IMuster16Abg[] = [{ id: 123 }];
          expectedResult = service.addMuster16AbgToCollectionIfMissing(muster16AbgCollection, ...muster16AbgArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const muster16Abg: IMuster16Abg = { id: 123 };
          const muster16Abg2: IMuster16Abg = { id: 456 };
          expectedResult = service.addMuster16AbgToCollectionIfMissing([], muster16Abg, muster16Abg2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Abg);
          expect(expectedResult).toContain(muster16Abg2);
        });

        it('should accept null and undefined values', () => {
          const muster16Abg: IMuster16Abg = { id: 123 };
          expectedResult = service.addMuster16AbgToCollectionIfMissing([], null, muster16Abg, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Abg);
        });

        it('should return initial array if no Muster16Abg is added', () => {
          const muster16AbgCollection: IMuster16Abg[] = [{ id: 123 }];
          expectedResult = service.addMuster16AbgToCollectionIfMissing(muster16AbgCollection, undefined, null);
          expect(expectedResult).toEqual(muster16AbgCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
