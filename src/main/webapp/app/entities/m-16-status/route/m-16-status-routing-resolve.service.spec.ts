jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IM16Status, M16Status } from '../m-16-status.model';
import { M16StatusService } from '../service/m-16-status.service';

import { M16StatusRoutingResolveService } from './m-16-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('M16Status routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: M16StatusRoutingResolveService;
    let service: M16StatusService;
    let resultM16Status: IM16Status | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(M16StatusRoutingResolveService);
      service = TestBed.inject(M16StatusService);
      resultM16Status = undefined;
    });

    describe('resolve', () => {
      it('should return IM16Status returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultM16Status = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultM16Status).toEqual({ id: 123 });
      });

      it('should return new IM16Status if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultM16Status = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultM16Status).toEqual(new M16Status());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as M16Status })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultM16Status = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultM16Status).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
