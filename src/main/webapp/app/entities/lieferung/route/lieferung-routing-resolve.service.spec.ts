jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILieferung, Lieferung } from '../lieferung.model';
import { LieferungService } from '../service/lieferung.service';

import { LieferungRoutingResolveService } from './lieferung-routing-resolve.service';

describe('Service Tests', () => {
  describe('Lieferung routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LieferungRoutingResolveService;
    let service: LieferungService;
    let resultLieferung: ILieferung | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LieferungRoutingResolveService);
      service = TestBed.inject(LieferungService);
      resultLieferung = undefined;
    });

    describe('resolve', () => {
      it('should return ILieferung returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLieferung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLieferung).toEqual({ id: 123 });
      });

      it('should return new ILieferung if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLieferung = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLieferung).toEqual(new Lieferung());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Lieferung })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLieferung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLieferung).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
