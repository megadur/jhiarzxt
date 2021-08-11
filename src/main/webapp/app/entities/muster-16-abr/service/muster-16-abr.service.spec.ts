import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMuster16Abr, Muster16Abr } from '../muster-16-abr.model';

import { Muster16AbrService } from './muster-16-abr.service';

describe('Service Tests', () => {
  describe('Muster16Abr Service', () => {
    let service: Muster16AbrService;
    let httpMock: HttpTestingController;
    let elemDefault: IMuster16Abr;
    let expectedResult: IMuster16Abr | IMuster16Abr[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Muster16AbrService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        betragRabA: 'AAAAAAA',
        betragRabH: 'AAAAAAA',
        betragApoAusz: 'AAAAAAA',
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

      it('should create a Muster16Abr', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Muster16Abr()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Muster16Abr', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            betragRabA: 'BBBBBB',
            betragRabH: 'BBBBBB',
            betragApoAusz: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Muster16Abr', () => {
        const patchObject = Object.assign(
          {
            betragRabH: 'BBBBBB',
          },
          new Muster16Abr()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Muster16Abr', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            betragRabA: 'BBBBBB',
            betragRabH: 'BBBBBB',
            betragApoAusz: 'BBBBBB',
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

      it('should delete a Muster16Abr', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMuster16AbrToCollectionIfMissing', () => {
        it('should add a Muster16Abr to an empty array', () => {
          const muster16Abr: IMuster16Abr = { id: 123 };
          expectedResult = service.addMuster16AbrToCollectionIfMissing([], muster16Abr);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Abr);
        });

        it('should not add a Muster16Abr to an array that contains it', () => {
          const muster16Abr: IMuster16Abr = { id: 123 };
          const muster16AbrCollection: IMuster16Abr[] = [
            {
              ...muster16Abr,
            },
            { id: 456 },
          ];
          expectedResult = service.addMuster16AbrToCollectionIfMissing(muster16AbrCollection, muster16Abr);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Muster16Abr to an array that doesn't contain it", () => {
          const muster16Abr: IMuster16Abr = { id: 123 };
          const muster16AbrCollection: IMuster16Abr[] = [{ id: 456 }];
          expectedResult = service.addMuster16AbrToCollectionIfMissing(muster16AbrCollection, muster16Abr);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Abr);
        });

        it('should add only unique Muster16Abr to an array', () => {
          const muster16AbrArray: IMuster16Abr[] = [{ id: 123 }, { id: 456 }, { id: 44049 }];
          const muster16AbrCollection: IMuster16Abr[] = [{ id: 123 }];
          expectedResult = service.addMuster16AbrToCollectionIfMissing(muster16AbrCollection, ...muster16AbrArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const muster16Abr: IMuster16Abr = { id: 123 };
          const muster16Abr2: IMuster16Abr = { id: 456 };
          expectedResult = service.addMuster16AbrToCollectionIfMissing([], muster16Abr, muster16Abr2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(muster16Abr);
          expect(expectedResult).toContain(muster16Abr2);
        });

        it('should accept null and undefined values', () => {
          const muster16Abr: IMuster16Abr = { id: 123 };
          expectedResult = service.addMuster16AbrToCollectionIfMissing([], null, muster16Abr, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(muster16Abr);
        });

        it('should return initial array if no Muster16Abr is added', () => {
          const muster16AbrCollection: IMuster16Abr[] = [{ id: 123 }];
          expectedResult = service.addMuster16AbrToCollectionIfMissing(muster16AbrCollection, undefined, null);
          expect(expectedResult).toEqual(muster16AbrCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
