jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBuildType, BuildType } from '../build-type.model';
import { BuildTypeService } from '../service/build-type.service';

import { BuildTypeRoutingResolveService } from './build-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('BuildType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BuildTypeRoutingResolveService;
    let service: BuildTypeService;
    let resultBuildType: IBuildType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BuildTypeRoutingResolveService);
      service = TestBed.inject(BuildTypeService);
      resultBuildType = undefined;
    });

    describe('resolve', () => {
      it('should return existing IBuildType for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new BuildType(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBuildType).toEqual(new BuildType(123));
      });

      it('should return new IBuildType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBuildType).toEqual(new BuildType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBuildType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBuildType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
