import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { BuildOrderState } from 'app/entities/enumerations/build-order-state.model';
import { IBuildOrder, BuildOrder } from '../build-order.model';

import { BuildOrderService } from './build-order.service';

describe('Service Tests', () => {
  describe('BuildOrder Service', () => {
    let service: BuildOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IBuildOrder;
    let expectedResult: IBuildOrder | IBuildOrder[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BuildOrderService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = new BuildOrder(0, BuildOrderState.PENDING, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            start: currentDate.format(DATE_FORMAT),
            finish: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BuildOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            start: currentDate.format(DATE_FORMAT),
            finish: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            start: currentDate,
            finish: currentDate,
          },
          returnedFromService
        );

        service.create(new BuildOrder()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BuildOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            state: 'BBBBBB',
            start: currentDate.format(DATE_FORMAT),
            finish: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            start: currentDate,
            finish: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BuildOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            state: 'BBBBBB',
            start: currentDate.format(DATE_FORMAT),
            finish: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            start: currentDate,
            finish: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BuildOrder', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
