jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRequisition, Requisition } from '../requisition.model';
import { RequisitionService } from '../service/requisition.service';

import { RequisitionRoutingResolveService } from './requisition-routing-resolve.service';

describe('Service Tests', () => {
  describe('Requisition routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RequisitionRoutingResolveService;
    let service: RequisitionService;
    let resultRequisition: IRequisition | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RequisitionRoutingResolveService);
      service = TestBed.inject(RequisitionService);
      resultRequisition = undefined;
    });

    describe('resolve', () => {
      it('should return existing IRequisition for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Requisition(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRequisition = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRequisition).toEqual(new Requisition(123));
      });

      it('should return new IRequisition if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRequisition = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRequisition).toEqual(new Requisition());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRequisition = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRequisition).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
