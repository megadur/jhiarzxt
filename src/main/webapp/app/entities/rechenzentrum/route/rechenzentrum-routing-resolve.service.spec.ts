jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRechenzentrum, Rechenzentrum } from '../rechenzentrum.model';
import { RechenzentrumService } from '../service/rechenzentrum.service';

import { RechenzentrumRoutingResolveService } from './rechenzentrum-routing-resolve.service';

describe('Service Tests', () => {
  describe('Rechenzentrum routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RechenzentrumRoutingResolveService;
    let service: RechenzentrumService;
    let resultRechenzentrum: IRechenzentrum | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RechenzentrumRoutingResolveService);
      service = TestBed.inject(RechenzentrumService);
      resultRechenzentrum = undefined;
    });

    describe('resolve', () => {
      it('should return IRechenzentrum returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRechenzentrum = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRechenzentrum).toEqual({ id: 123 });
      });

      it('should return new IRechenzentrum if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRechenzentrum = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRechenzentrum).toEqual(new Rechenzentrum());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Rechenzentrum })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRechenzentrum = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRechenzentrum).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
