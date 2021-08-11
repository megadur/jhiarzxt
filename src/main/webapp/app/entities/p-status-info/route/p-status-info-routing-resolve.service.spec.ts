jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPStatusInfo, PStatusInfo } from '../p-status-info.model';
import { PStatusInfoService } from '../service/p-status-info.service';

import { PStatusInfoRoutingResolveService } from './p-status-info-routing-resolve.service';

describe('Service Tests', () => {
  describe('PStatusInfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PStatusInfoRoutingResolveService;
    let service: PStatusInfoService;
    let resultPStatusInfo: IPStatusInfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PStatusInfoRoutingResolveService);
      service = TestBed.inject(PStatusInfoService);
      resultPStatusInfo = undefined;
    });

    describe('resolve', () => {
      it('should return IPStatusInfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatusInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPStatusInfo).toEqual({ id: 123 });
      });

      it('should return new IPStatusInfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatusInfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPStatusInfo).toEqual(new PStatusInfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PStatusInfo })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPStatusInfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPStatusInfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
