import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPRezeptAbr, PRezeptAbr } from '../p-rezept-abr.model';

import { PRezeptAbrService } from './p-rezept-abr.service';

describe('Service Tests', () => {
  describe('PRezeptAbr Service', () => {
    let service: PRezeptAbrService;
    let httpMock: HttpTestingController;
    let elemDefault: IPRezeptAbr;
    let expectedResult: IPRezeptAbr | IPRezeptAbr[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PRezeptAbrService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pRezeptId: 'AAAAAAA',
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

      it('should create a PRezeptAbr', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PRezeptAbr()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PRezeptAbr', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
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

      it('should partial update a PRezeptAbr', () => {
        const patchObject = Object.assign(
          {
            pRezeptId: 'BBBBBB',
            betragRabH: 'BBBBBB',
          },
          new PRezeptAbr()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PRezeptAbr', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId: 'BBBBBB',
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

      it('should delete a PRezeptAbr', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPRezeptAbrToCollectionIfMissing', () => {
        it('should add a PRezeptAbr to an empty array', () => {
          const pRezeptAbr: IPRezeptAbr = { id: 123 };
          expectedResult = service.addPRezeptAbrToCollectionIfMissing([], pRezeptAbr);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptAbr);
        });

        it('should not add a PRezeptAbr to an array that contains it', () => {
          const pRezeptAbr: IPRezeptAbr = { id: 123 };
          const pRezeptAbrCollection: IPRezeptAbr[] = [
            {
              ...pRezeptAbr,
            },
            { id: 456 },
          ];
          expectedResult = service.addPRezeptAbrToCollectionIfMissing(pRezeptAbrCollection, pRezeptAbr);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PRezeptAbr to an array that doesn't contain it", () => {
          const pRezeptAbr: IPRezeptAbr = { id: 123 };
          const pRezeptAbrCollection: IPRezeptAbr[] = [{ id: 456 }];
          expectedResult = service.addPRezeptAbrToCollectionIfMissing(pRezeptAbrCollection, pRezeptAbr);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptAbr);
        });

        it('should add only unique PRezeptAbr to an array', () => {
          const pRezeptAbrArray: IPRezeptAbr[] = [{ id: 123 }, { id: 456 }, { id: 56764 }];
          const pRezeptAbrCollection: IPRezeptAbr[] = [{ id: 123 }];
          expectedResult = service.addPRezeptAbrToCollectionIfMissing(pRezeptAbrCollection, ...pRezeptAbrArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pRezeptAbr: IPRezeptAbr = { id: 123 };
          const pRezeptAbr2: IPRezeptAbr = { id: 456 };
          expectedResult = service.addPRezeptAbrToCollectionIfMissing([], pRezeptAbr, pRezeptAbr2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptAbr);
          expect(expectedResult).toContain(pRezeptAbr2);
        });

        it('should accept null and undefined values', () => {
          const pRezeptAbr: IPRezeptAbr = { id: 123 };
          expectedResult = service.addPRezeptAbrToCollectionIfMissing([], null, pRezeptAbr, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptAbr);
        });

        it('should return initial array if no PRezeptAbr is added', () => {
          const pRezeptAbrCollection: IPRezeptAbr[] = [{ id: 123 }];
          expectedResult = service.addPRezeptAbrToCollectionIfMissing(pRezeptAbrCollection, undefined, null);
          expect(expectedResult).toEqual(pRezeptAbrCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
