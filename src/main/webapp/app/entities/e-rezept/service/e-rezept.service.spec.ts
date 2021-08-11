import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IERezept, ERezept } from '../e-rezept.model';

import { ERezeptService } from './e-rezept.service';

describe('Service Tests', () => {
  describe('ERezept Service', () => {
    let service: ERezeptService;
    let httpMock: HttpTestingController;
    let elemDefault: IERezept;
    let expectedResult: IERezept | IERezept[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ERezeptService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        iD: 'AAAAAAA',
        dokVer: 'AAAAAAA',
        abgInfo: 'AAAAAAA',
        abgDatum: currentDate,
        abgGesZuzahl: 0,
        abgGesBrutto: 0,
        abgVertragskz: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            abgDatum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ERezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            abgDatum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            abgDatum: currentDate,
          },
          returnedFromService
        );

        service.create(new ERezept()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ERezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            dokVer: 'BBBBBB',
            abgInfo: 'BBBBBB',
            abgDatum: currentDate.format(DATE_FORMAT),
            abgGesZuzahl: 1,
            abgGesBrutto: 1,
            abgVertragskz: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            abgDatum: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ERezept', () => {
        const patchObject = Object.assign(
          {
            abgInfo: 'BBBBBB',
            abgGesZuzahl: 1,
            abgGesBrutto: 1,
            abgVertragskz: 'BBBBBB',
          },
          new ERezept()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            abgDatum: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ERezept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            dokVer: 'BBBBBB',
            abgInfo: 'BBBBBB',
            abgDatum: currentDate.format(DATE_FORMAT),
            abgGesZuzahl: 1,
            abgGesBrutto: 1,
            abgVertragskz: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            abgDatum: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ERezept', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addERezeptToCollectionIfMissing', () => {
        it('should add a ERezept to an empty array', () => {
          const eRezept: IERezept = { id: 123 };
          expectedResult = service.addERezeptToCollectionIfMissing([], eRezept);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eRezept);
        });

        it('should not add a ERezept to an array that contains it', () => {
          const eRezept: IERezept = { id: 123 };
          const eRezeptCollection: IERezept[] = [
            {
              ...eRezept,
            },
            { id: 456 },
          ];
          expectedResult = service.addERezeptToCollectionIfMissing(eRezeptCollection, eRezept);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ERezept to an array that doesn't contain it", () => {
          const eRezept: IERezept = { id: 123 };
          const eRezeptCollection: IERezept[] = [{ id: 456 }];
          expectedResult = service.addERezeptToCollectionIfMissing(eRezeptCollection, eRezept);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eRezept);
        });

        it('should add only unique ERezept to an array', () => {
          const eRezeptArray: IERezept[] = [{ id: 123 }, { id: 456 }, { id: 37820 }];
          const eRezeptCollection: IERezept[] = [{ id: 123 }];
          expectedResult = service.addERezeptToCollectionIfMissing(eRezeptCollection, ...eRezeptArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const eRezept: IERezept = { id: 123 };
          const eRezept2: IERezept = { id: 456 };
          expectedResult = service.addERezeptToCollectionIfMissing([], eRezept, eRezept2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eRezept);
          expect(expectedResult).toContain(eRezept2);
        });

        it('should accept null and undefined values', () => {
          const eRezept: IERezept = { id: 123 };
          expectedResult = service.addERezeptToCollectionIfMissing([], null, eRezept, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eRezept);
        });

        it('should return initial array if no ERezept is added', () => {
          const eRezeptCollection: IERezept[] = [{ id: 123 }];
          expectedResult = service.addERezeptToCollectionIfMissing(eRezeptCollection, undefined, null);
          expect(expectedResult).toEqual(eRezeptCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
