jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBuildOrder, BuildOrder } from '../build-order.model';
import { BuildOrderService } from '../service/build-order.service';

import { BuildOrderRoutingResolveService } from './build-order-routing-resolve.service';

describe('Service Tests', () => {
  describe('BuildOrder routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BuildOrderRoutingResolveService;
    let service: BuildOrderService;
    let resultBuildOrder: IBuildOrder | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BuildOrderRoutingResolveService);
      service = TestBed.inject(BuildOrderService);
      resultBuildOrder = undefined;
    });

    describe('resolve', () => {
      it('should return existing IBuildOrder for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new BuildOrder(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildOrder = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBuildOrder).toEqual(new BuildOrder(123));
      });

      it('should return new IBuildOrder if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildOrder = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBuildOrder).toEqual(new BuildOrder());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildOrder = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBuildOrder).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
