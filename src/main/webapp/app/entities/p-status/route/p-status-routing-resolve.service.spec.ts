jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPStatus, PStatus } from '../p-status.model';
import { PStatusService } from '../service/p-status.service';

import { PStatusRoutingResolveService } from './p-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('PStatus routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PStatusRoutingResolveService;
    let service: PStatusService;
    let resultPStatus: IPStatus | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PStatusRoutingResolveService);
      service = TestBed.inject(PStatusService);
      resultPStatus = undefined;
    });

    describe('resolve', () => {
      it('should return IPStatus returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPStatus).toEqual({ id: 123 });
      });

      it('should return new IPStatus if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatus = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPStatus).toEqual(new PStatus());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PStatus })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPStatus).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
