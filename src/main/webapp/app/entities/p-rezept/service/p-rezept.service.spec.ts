import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPRezept, PRezept } from '../p-rezept.model';

import { PRezeptService } from './p-rezept.service';

describe('Service Tests', () => {
  describe('PRezept Service', () => {
    let service: PRezeptService;
    let httpMock: HttpTestingController;
    let elemDefault: IPRezept;
    let expectedResult: IPRezept | IPRezept[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PRezeptService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pRezeptId: 'AAAAAAA',
        lieferdat: 'AAAAAAA',
        lieferungId: 'AAAAAAA',
        aPeriode: 'AAAAAAA',
        abDatum: 'AAAAAAA',
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

      it('should create a PRezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PRezept()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PRezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
            lieferdat: 'BBBBBB',
            lieferungId: 'BBBBBB',
            aPeriode: 'BBBBBB',
            abDatum: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PRezept', () => {
        const patchObject = Object.assign(
          {
            aPeriode: 'BBBBBB',
            abDatum: 'BBBBBB',
          },
          new PRezept()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PRezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
            lieferdat: 'BBBBBB',
            lieferungId: 'BBBBBB',
            aPeriode: 'BBBBBB',
            abDatum: 'BBBBBB',
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

      it('should delete a PRezept', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPRezeptToCollectionIfMissing', () => {
        it('should add a PRezept to an empty array', () => {
          const pRezept: IPRezept = { id: 123 };
          expectedResult = service.addPRezeptToCollectionIfMissing([], pRezept);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezept);
        });

        it('should not add a PRezept to an array that contains it', () => {
          const pRezept: IPRezept = { id: 123 };
          const pRezeptCollection: IPRezept[] = [
            {
              ...pRezept,
            },
            { id: 456 },
          ];
          expectedResult = service.addPRezeptToCollectionIfMissing(pRezeptCollection, pRezept);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PRezept to an array that doesn't contain it", () => {
          const pRezept: IPRezept = { id: 123 };
          const pRezeptCollection: IPRezept[] = [{ id: 456 }];
          expectedResult = service.addPRezeptToCollectionIfMissing(pRezeptCollection, pRezept);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezept);
        });

        it('should add only unique PRezept to an array', () => {
          const pRezeptArray: IPRezept[] = [{ id: 123 }, { id: 456 }, { id: 94901 }];
          const pRezeptCollection: IPRezept[] = [{ id: 123 }];
          expectedResult = service.addPRezeptToCollectionIfMissing(pRezeptCollection, ...pRezeptArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pRezept: IPRezept = { id: 123 };
          const pRezept2: IPRezept = { id: 456 };
          expectedResult = service.addPRezeptToCollectionIfMissing([], pRezept, pRezept2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezept);
          expect(expectedResult).toContain(pRezept2);
        });

        it('should accept null and undefined values', () => {
          const pRezept: IPRezept = { id: 123 };
          expectedResult = service.addPRezeptToCollectionIfMissing([], null, pRezept, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezept);
        });

        it('should return initial array if no PRezept is added', () => {
          const pRezeptCollection: IPRezept[] = [{ id: 123 }];
          expectedResult = service.addPRezeptToCollectionIfMissing(pRezeptCollection, undefined, null);
          expect(expectedResult).toEqual(pRezeptCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
