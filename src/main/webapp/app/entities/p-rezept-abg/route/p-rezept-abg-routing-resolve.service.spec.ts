jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPRezeptAbg, PRezeptAbg } from '../p-rezept-abg.model';
import { PRezeptAbgService } from '../service/p-rezept-abg.service';

import { PRezeptAbgRoutingResolveService } from './p-rezept-abg-routing-resolve.service';

describe('Service Tests', () => {
  describe('PRezeptAbg routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PRezeptAbgRoutingResolveService;
    let service: PRezeptAbgService;
    let resultPRezeptAbg: IPRezeptAbg | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PRezeptAbgRoutingResolveService);
      service = TestBed.inject(PRezeptAbgService);
      resultPRezeptAbg = undefined;
    });

    describe('resolve', () => {
      it('should return IPRezeptAbg returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptAbg = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezeptAbg).toEqual({ id: 123 });
      });

      it('should return new IPRezeptAbg if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptAbg = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPRezeptAbg).toEqual(new PRezeptAbg());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PRezeptAbg })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptAbg = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezeptAbg).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
