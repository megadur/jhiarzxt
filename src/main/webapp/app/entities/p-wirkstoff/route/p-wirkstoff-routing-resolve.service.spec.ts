jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPWirkstoff, PWirkstoff } from '../p-wirkstoff.model';
import { PWirkstoffService } from '../service/p-wirkstoff.service';

import { PWirkstoffRoutingResolveService } from './p-wirkstoff-routing-resolve.service';

describe('Service Tests', () => {
  describe('PWirkstoff routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PWirkstoffRoutingResolveService;
    let service: PWirkstoffService;
    let resultPWirkstoff: IPWirkstoff | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PWirkstoffRoutingResolveService);
      service = TestBed.inject(PWirkstoffService);
      resultPWirkstoff = undefined;
    });

    describe('resolve', () => {
      it('should return IPWirkstoff returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPWirkstoff = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPWirkstoff).toEqual({ id: 123 });
      });

      it('should return new IPWirkstoff if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPWirkstoff = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPWirkstoff).toEqual(new PWirkstoff());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PWirkstoff })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPWirkstoff = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPWirkstoff).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
