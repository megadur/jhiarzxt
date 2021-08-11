import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILieferung, Lieferung } from '../lieferung.model';

import { LieferungService } from './lieferung.service';

describe('Service Tests', () => {
  describe('Lieferung Service', () => {
    let service: LieferungService;
    let httpMock: HttpTestingController;
    let elemDefault: ILieferung;
    let expectedResult: ILieferung | ILieferung[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LieferungService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        iD: 'AAAAAAA',
        datum: currentDate,
        apoIk: 'AAAAAAA',
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

      it('should create a Lieferung', () => {
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

        service.create(new Lieferung()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lieferung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            datum: currentDate.format(DATE_FORMAT),
            apoIk: 'BBBBBB',
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

      it('should partial update a Lieferung', () => {
        const patchObject = Object.assign(
          {
            iD: 'BBBBBB',
            datum: currentDate.format(DATE_FORMAT),
          },
          new Lieferung()
        );

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

      it('should return a list of Lieferung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iD: 'BBBBBB',
            datum: currentDate.format(DATE_FORMAT),
            apoIk: 'BBBBBB',
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

      it('should delete a Lieferung', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLieferungToCollectionIfMissing', () => {
        it('should add a Lieferung to an empty array', () => {
          const lieferung: ILieferung = { id: 123 };
          expectedResult = service.addLieferungToCollectionIfMissing([], lieferung);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(lieferung);
        });

        it('should not add a Lieferung to an array that contains it', () => {
          const lieferung: ILieferung = { id: 123 };
          const lieferungCollection: ILieferung[] = [
            {
              ...lieferung,
            },
            { id: 456 },
          ];
          expectedResult = service.addLieferungToCollectionIfMissing(lieferungCollection, lieferung);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Lieferung to an array that doesn't contain it", () => {
          const lieferung: ILieferung = { id: 123 };
          const lieferungCollection: ILieferung[] = [{ id: 456 }];
          expectedResult = service.addLieferungToCollectionIfMissing(lieferungCollection, lieferung);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(lieferung);
        });

        it('should add only unique Lieferung to an array', () => {
          const lieferungArray: ILieferung[] = [{ id: 123 }, { id: 456 }, { id: 62472 }];
          const lieferungCollection: ILieferung[] = [{ id: 123 }];
          expectedResult = service.addLieferungToCollectionIfMissing(lieferungCollection, ...lieferungArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const lieferung: ILieferung = { id: 123 };
          const lieferung2: ILieferung = { id: 456 };
          expectedResult = service.addLieferungToCollectionIfMissing([], lieferung, lieferung2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(lieferung);
          expect(expectedResult).toContain(lieferung2);
        });

        it('should accept null and undefined values', () => {
          const lieferung: ILieferung = { id: 123 };
          expectedResult = service.addLieferungToCollectionIfMissing([], null, lieferung, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(lieferung);
        });

        it('should return initial array if no Lieferung is added', () => {
          const lieferungCollection: ILieferung[] = [{ id: 123 }];
          expectedResult = service.addLieferungToCollectionIfMissing(lieferungCollection, undefined, null);
          expect(expectedResult).toEqual(lieferungCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
