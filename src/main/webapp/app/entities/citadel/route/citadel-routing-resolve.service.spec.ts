jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICitadel, Citadel } from '../citadel.model';
import { CitadelService } from '../service/citadel.service';

import { CitadelRoutingResolveService } from './citadel-routing-resolve.service';

describe('Service Tests', () => {
  describe('Citadel routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CitadelRoutingResolveService;
    let service: CitadelService;
    let resultCitadel: ICitadel | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CitadelRoutingResolveService);
      service = TestBed.inject(CitadelService);
      resultCitadel = undefined;
    });

    describe('resolve', () => {
      it('should return existing ICitadel for existing id', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: new Citadel(id) })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCitadel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCitadel).toEqual(new Citadel(123));
      });

      it('should return new ICitadel if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCitadel = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCitadel).toEqual(new Citadel());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCitadel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCitadel).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
