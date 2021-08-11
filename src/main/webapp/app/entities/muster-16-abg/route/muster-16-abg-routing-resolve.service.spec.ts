jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMuster16Abg, Muster16Abg } from '../muster-16-abg.model';
import { Muster16AbgService } from '../service/muster-16-abg.service';

import { Muster16AbgRoutingResolveService } from './muster-16-abg-routing-resolve.service';

describe('Service Tests', () => {
  describe('Muster16Abg routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Muster16AbgRoutingResolveService;
    let service: Muster16AbgService;
    let resultMuster16Abg: IMuster16Abg | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Muster16AbgRoutingResolveService);
      service = TestBed.inject(Muster16AbgService);
      resultMuster16Abg = undefined;
    });

    describe('resolve', () => {
      it('should return IMuster16Abg returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abg = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16Abg).toEqual({ id: 123 });
      });

      it('should return new IMuster16Abg if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abg = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMuster16Abg).toEqual(new Muster16Abg());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Muster16Abg })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abg = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16Abg).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
