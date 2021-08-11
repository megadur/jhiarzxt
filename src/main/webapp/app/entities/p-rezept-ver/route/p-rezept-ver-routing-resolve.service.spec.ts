jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPRezeptVer, PRezeptVer } from '../p-rezept-ver.model';
import { PRezeptVerService } from '../service/p-rezept-ver.service';

import { PRezeptVerRoutingResolveService } from './p-rezept-ver-routing-resolve.service';

describe('Service Tests', () => {
  describe('PRezeptVer routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PRezeptVerRoutingResolveService;
    let service: PRezeptVerService;
    let resultPRezeptVer: IPRezeptVer | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PRezeptVerRoutingResolveService);
      service = TestBed.inject(PRezeptVerService);
      resultPRezeptVer = undefined;
    });

    describe('resolve', () => {
      it('should return IPRezeptVer returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptVer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezeptVer).toEqual({ id: 123 });
      });

      it('should return new IPRezeptVer if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptVer = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPRezeptVer).toEqual(new PRezeptVer());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PRezeptVer })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezeptVer = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezeptVer).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
