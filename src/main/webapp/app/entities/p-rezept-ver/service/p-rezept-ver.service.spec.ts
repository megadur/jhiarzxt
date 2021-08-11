import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPRezeptVer, PRezeptVer } from '../p-rezept-ver.model';

import { PRezeptVerService } from './p-rezept-ver.service';

describe('Service Tests', () => {
  describe('PRezeptVer Service', () => {
    let service: PRezeptVerService;
    let httpMock: HttpTestingController;
    let elemDefault: IPRezeptVer;
    let expectedResult: IPRezeptVer | IPRezeptVer[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PRezeptVerService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pRezeptId9: 'AAAAAAA',
        apoIk: 'AAAAAAA',
        apoIkSend: 'AAAAAAA',
        arbPlatz: 'AAAAAAA',
        avsId: 'AAAAAAA',
        bediener: 'AAAAAAA',
        bvg: 'AAAAAAA',
        eStatus: 'AAAAAAA',
        erstZeitpunkt: 'AAAAAAA',
        kName: 'AAAAAAA',
        kkIk: 'AAAAAAA',
        laNr: 'AAAAAAA',
        vrsNr: 'AAAAAAA',
        vrtrgsArztNr: 'AAAAAAA',
        vGeb: 'AAAAAAA',
        vStat: 'AAAAAAA',
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

      it('should create a PRezeptVer', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PRezeptVer()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PRezeptVer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId9: 'BBBBBB',
            apoIk: 'BBBBBB',
            apoIkSend: 'BBBBBB',
            arbPlatz: 'BBBBBB',
            avsId: 'BBBBBB',
            bediener: 'BBBBBB',
            bvg: 'BBBBBB',
            eStatus: 'BBBBBB',
            erstZeitpunkt: 'BBBBBB',
            kName: 'BBBBBB',
            kkIk: 'BBBBBB',
            laNr: 'BBBBBB',
            vrsNr: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            vGeb: 'BBBBBB',
            vStat: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PRezeptVer', () => {
        const patchObject = Object.assign(
          {
            pRezeptId9: 'BBBBBB',
            apoIkSend: 'BBBBBB',
            arbPlatz: 'BBBBBB',
            bediener: 'BBBBBB',
            eStatus: 'BBBBBB',
            kName: 'BBBBBB',
            laNr: 'BBBBBB',
            vrsNr: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            vGeb: 'BBBBBB',
          },
          new PRezeptVer()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PRezeptVer', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pRezeptId9: 'BBBBBB',
            apoIk: 'BBBBBB',
            apoIkSend: 'BBBBBB',
            arbPlatz: 'BBBBBB',
            avsId: 'BBBBBB',
            bediener: 'BBBBBB',
            bvg: 'BBBBBB',
            eStatus: 'BBBBBB',
            erstZeitpunkt: 'BBBBBB',
            kName: 'BBBBBB',
            kkIk: 'BBBBBB',
            laNr: 'BBBBBB',
            vrsNr: 'BBBBBB',
            vrtrgsArztNr: 'BBBBBB',
            vGeb: 'BBBBBB',
            vStat: 'BBBBBB',
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

      it('should delete a PRezeptVer', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPRezeptVerToCollectionIfMissing', () => {
        it('should add a PRezeptVer to an empty array', () => {
          const pRezeptVer: IPRezeptVer = { id: 123 };
          expectedResult = service.addPRezeptVerToCollectionIfMissing([], pRezeptVer);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptVer);
        });

        it('should not add a PRezeptVer to an array that contains it', () => {
          const pRezeptVer: IPRezeptVer = { id: 123 };
          const pRezeptVerCollection: IPRezeptVer[] = [
            {
              ...pRezeptVer,
            },
            { id: 456 },
          ];
          expectedResult = service.addPRezeptVerToCollectionIfMissing(pRezeptVerCollection, pRezeptVer);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PRezeptVer to an array that doesn't contain it", () => {
          const pRezeptVer: IPRezeptVer = { id: 123 };
          const pRezeptVerCollection: IPRezeptVer[] = [{ id: 456 }];
          expectedResult = service.addPRezeptVerToCollectionIfMissing(pRezeptVerCollection, pRezeptVer);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptVer);
        });

        it('should add only unique PRezeptVer to an array', () => {
          const pRezeptVerArray: IPRezeptVer[] = [{ id: 123 }, { id: 456 }, { id: 7586 }];
          const pRezeptVerCollection: IPRezeptVer[] = [{ id: 123 }];
          expectedResult = service.addPRezeptVerToCollectionIfMissing(pRezeptVerCollection, ...pRezeptVerArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pRezeptVer: IPRezeptVer = { id: 123 };
          const pRezeptVer2: IPRezeptVer = { id: 456 };
          expectedResult = service.addPRezeptVerToCollectionIfMissing([], pRezeptVer, pRezeptVer2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pRezeptVer);
          expect(expectedResult).toContain(pRezeptVer2);
        });

        it('should accept null and undefined values', () => {
          const pRezeptVer: IPRezeptVer = { id: 123 };
          expectedResult = service.addPRezeptVerToCollectionIfMissing([], null, pRezeptVer, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pRezeptVer);
        });

        it('should return initial array if no PRezeptVer is added', () => {
          const pRezeptVerCollection: IPRezeptVer[] = [{ id: 123 }];
          expectedResult = service.addPRezeptVerToCollectionIfMissing(pRezeptVerCollection, undefined, null);
          expect(expectedResult).toEqual(pRezeptVerCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
