jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMuster16, Muster16 } from '../muster-16.model';
import { Muster16Service } from '../service/muster-16.service';

import { Muster16RoutingResolveService } from './muster-16-routing-resolve.service';

describe('Service Tests', () => {
  describe('Muster16 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Muster16RoutingResolveService;
    let service: Muster16Service;
    let resultMuster16: IMuster16 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Muster16RoutingResolveService);
      service = TestBed.inject(Muster16Service);
      resultMuster16 = undefined;
    });

    describe('resolve', () => {
      it('should return IMuster16 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16).toEqual({ id: 123 });
      });

      it('should return new IMuster16 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMuster16).toEqual(new Muster16());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Muster16 })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
