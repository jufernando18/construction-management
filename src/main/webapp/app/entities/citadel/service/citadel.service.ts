import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { ICitadel } from '../citadel.model';

type EntityResponseType = HttpResponse<ICitadel>;
type EntityArrayResponseType = HttpResponse<ICitadel[]>;

@Injectable({ providedIn: 'root' })
export class CitadelService {
  public resourceUrl = SERVER_API_URL + 'api/citadels';

  constructor(protected http: HttpClient) {}

  create(citadel: ICitadel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citadel);
    return this.http
      .post<ICitadel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(citadel: ICitadel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citadel);
    return this.http
      .put<ICitadel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICitadel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICitadel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(citadel: ICitadel): ICitadel {
    const copy: ICitadel = Object.assign({}, citadel, {
      start: citadel.start?.isValid() ? citadel.start.format(DATE_FORMAT) : undefined,
      finish: citadel.finish?.isValid() ? citadel.finish.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((citadel: ICitadel) => {
        citadel.start = citadel.start ? dayjs(citadel.start) : undefined;
        citadel.finish = citadel.finish ? dayjs(citadel.finish) : undefined;
      });
    }
    return res;
  }
}
