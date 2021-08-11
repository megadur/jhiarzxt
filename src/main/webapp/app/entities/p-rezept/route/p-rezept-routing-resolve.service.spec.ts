jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPRezept, PRezept } from '../p-rezept.model';
import { PRezeptService } from '../service/p-rezept.service';

import { PRezeptRoutingResolveService } from './p-rezept-routing-resolve.service';

describe('Service Tests', () => {
  describe('PRezept routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PRezeptRoutingResolveService;
    let service: PRezeptService;
    let resultPRezept: IPRezept | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PRezeptRoutingResolveService);
      service = TestBed.inject(PRezeptService);
      resultPRezept = undefined;
    });

    describe('resolve', () => {
      it('should return IPRezept returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezept).toEqual({ id: 123 });
      });

      it('should return new IPRezept if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezept = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPRezept).toEqual(new PRezept());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PRezept })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPRezept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPRezept).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
