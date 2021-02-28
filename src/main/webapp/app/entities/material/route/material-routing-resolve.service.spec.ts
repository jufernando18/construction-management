jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMaterial, Material } from '../material.model';
import { MaterialService } from '../service/material.service';

import { MaterialRoutingResolveService } from './material-routing-resolve.service';

describe('Service Tests', () => {
  describe('Material routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MaterialRoutingResolveService;
    let service: MaterialService;
    let resultMaterial: IMaterial | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MaterialRoutingResolveService);
      service = TestBed.inject(MaterialService);
      resultMaterial = undefined;
    });

    describe('resolve', () => {
      it('should return existing IMaterial for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Material(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMaterial = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMaterial).toEqual(new Material(123));
      });

      it('should return new IMaterial if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMaterial = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMaterial).toEqual(new Material());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMaterial = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMaterial).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
