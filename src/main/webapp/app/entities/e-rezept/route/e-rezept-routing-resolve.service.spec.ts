jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IERezept, ERezept } from '../e-rezept.model';
import { ERezeptService } from '../service/e-rezept.service';

import { ERezeptRoutingResolveService } from './e-rezept-routing-resolve.service';

describe('Service Tests', () => {
  describe('ERezept routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ERezeptRoutingResolveService;
    let service: ERezeptService;
    let resultERezept: IERezept | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ERezeptRoutingResolveService);
      service = TestBed.inject(ERezeptService);
      resultERezept = undefined;
    });

    describe('resolve', () => {
      it('should return IERezept returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultERezept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultERezept).toEqual({ id: 123 });
      });

      it('should return new IERezept if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultERezept = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultERezept).toEqual(new ERezept());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ERezept })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultERezept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultERezept).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
