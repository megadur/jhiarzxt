import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEAenderung, EAenderung } from '../e-aenderung.model';

import { EAenderungService } from './e-aenderung.service';

describe('Service Tests', () => {
  describe('EAenderung Service', () => {
    let service: EAenderungService;
    let httpMock: HttpTestingController;
    let elemDefault: IEAenderung;
    let expectedResult: IEAenderung | IEAenderung[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EAenderungService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        schluessel: 0,
        dokuRezept: 'AAAAAAA',
        dokuArzt: 'AAAAAAA',
        datum: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EAenderung', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.create(new EAenderung()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EAenderung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            schluessel: 1,
            dokuRezept: 'BBBBBB',
            dokuArzt: 'BBBBBB',
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EAenderung', () => {
        const patchObject = Object.assign({}, new EAenderung());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EAenderung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            schluessel: 1,
            dokuRezept: 'BBBBBB',
            dokuArzt: 'BBBBBB',
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EAenderung', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEAenderungToCollectionIfMissing', () => {
        it('should add a EAenderung to an empty array', () => {
          const eAenderung: IEAenderung = { id: 123 };
          expectedResult = service.addEAenderungToCollectionIfMissing([], eAenderung);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eAenderung);
        });

        it('should not add a EAenderung to an array that contains it', () => {
          const eAenderung: IEAenderung = { id: 123 };
          const eAenderungCollection: IEAenderung[] = [
            {
              ...eAenderung,
            },
            { id: 456 },
          ];
          expectedResult = service.addEAenderungToCollectionIfMissing(eAenderungCollection, eAenderung);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EAenderung to an array that doesn't contain it", () => {
          const eAenderung: IEAenderung = { id: 123 };
          const eAenderungCollection: IEAenderung[] = [{ id: 456 }];
          expectedResult = service.addEAenderungToCollectionIfMissing(eAenderungCollection, eAenderung);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eAenderung);
        });

        it('should add only unique EAenderung to an array', () => {
          const eAenderungArray: IEAenderung[] = [{ id: 123 }, { id: 456 }, { id: 67526 }];
          const eAenderungCollection: IEAenderung[] = [{ id: 123 }];
          expectedResult = service.addEAenderungToCollectionIfMissing(eAenderungCollection, ...eAenderungArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const eAenderung: IEAenderung = { id: 123 };
          const eAenderung2: IEAenderung = { id: 456 };
          expectedResult = service.addEAenderungToCollectionIfMissing([], eAenderung, eAenderung2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(eAenderung);
          expect(expectedResult).toContain(eAenderung2);
        });

        it('should accept null and undefined values', () => {
          const eAenderung: IEAenderung = { id: 123 };
          expectedResult = service.addEAenderungToCollectionIfMissing([], null, eAenderung, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(eAenderung);
        });

        it('should return initial array if no EAenderung is added', () => {
          const eAenderungCollection: IEAenderung[] = [{ id: 123 }];
          expectedResult = service.addEAenderungToCollectionIfMissing(eAenderungCollection, undefined, null);
          expect(expectedResult).toEqual(eAenderungCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
