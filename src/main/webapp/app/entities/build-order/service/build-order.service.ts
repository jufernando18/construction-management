import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IBuildOrder } from '../build-order.model';

type EntityResponseType = HttpResponse<IBuildOrder>;
type EntityArrayResponseType = HttpResponse<IBuildOrder[]>;

@Injectable({ providedIn: 'root' })
export class BuildOrderService {
  public resourceUrl = SERVER_API_URL + 'api/build-orders';

  constructor(protected http: HttpClient) {}

  create(buildOrder: IBuildOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(buildOrder);
    return this.http
      .post<IBuildOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(buildOrder: IBuildOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(buildOrder);
    return this.http
      .put<IBuildOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBuildOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBuildOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(buildOrder: IBuildOrder): IBuildOrder {
    const copy: IBuildOrder = Object.assign({}, buildOrder, {
      start: buildOrder.start?.isValid() ? buildOrder.start.format(DATE_FORMAT) : undefined,
      finish: buildOrder.finish?.isValid() ? buildOrder.finish.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.start = res.body.start ? dayjs(res.body.start) : undefined;
      res.body.finish = res.body.finish ? dayjs(res.body.finish) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((buildOrder: IBuildOrder) => {
        buildOrder.start = buildOrder.start ? dayjs(buildOrder.start) : undefined;
        buildOrder.finish = buildOrder.finish ? dayjs(buildOrder.finish) : undefined;
      });
    }
    return res;
  }
}
