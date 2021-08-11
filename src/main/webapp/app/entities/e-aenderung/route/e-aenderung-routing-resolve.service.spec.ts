jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEAenderung, EAenderung } from '../e-aenderung.model';
import { EAenderungService } from '../service/e-aenderung.service';

import { EAenderungRoutingResolveService } from './e-aenderung-routing-resolve.service';

describe('Service Tests', () => {
  describe('EAenderung routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EAenderungRoutingResolveService;
    let service: EAenderungService;
    let resultEAenderung: IEAenderung | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EAenderungRoutingResolveService);
      service = TestBed.inject(EAenderungService);
      resultEAenderung = undefined;
    });

    describe('resolve', () => {
      it('should return IEAenderung returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEAenderung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEAenderung).toEqual({ id: 123 });
      });

      it('should return new IEAenderung if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEAenderung = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEAenderung).toEqual(new EAenderung());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EAenderung })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEAenderung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEAenderung).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
