jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMuster16Abr, Muster16Abr } from '../muster-16-abr.model';
import { Muster16AbrService } from '../service/muster-16-abr.service';

import { Muster16AbrRoutingResolveService } from './muster-16-abr-routing-resolve.service';

describe('Service Tests', () => {
  describe('Muster16Abr routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Muster16AbrRoutingResolveService;
    let service: Muster16AbrService;
    let resultMuster16Abr: IMuster16Abr | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Muster16AbrRoutingResolveService);
      service = TestBed.inject(Muster16AbrService);
      resultMuster16Abr = undefined;
    });

    describe('resolve', () => {
      it('should return IMuster16Abr returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abr = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16Abr).toEqual({ id: 123 });
      });

      it('should return new IMuster16Abr if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abr = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMuster16Abr).toEqual(new Muster16Abr());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Muster16Abr })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMuster16Abr = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMuster16Abr).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
