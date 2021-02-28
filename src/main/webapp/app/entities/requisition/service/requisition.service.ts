import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequisition } from '../requisition.model';

type EntityResponseType = HttpResponse<IRequisition>;
type EntityArrayResponseType = HttpResponse<IRequisition[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionService {
  public resourceUrl = SERVER_API_URL + 'api/requisitions';

  constructor(protected http: HttpClient) {}

  create(requisition: IRequisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requisition);
    return this.http
      .post<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requisition: IRequisition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requisition);
    return this.http
      .put<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequisition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequisition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(requisition: IRequisition): IRequisition {
    const copy: IRequisition = Object.assign({}, requisition, {
      date: requisition.date?.isValid() ? requisition.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((requisition: IRequisition) => {
        requisition.date = requisition.date ? dayjs(requisition.date) : undefined;
      });
    }
    return res;
  }
}
