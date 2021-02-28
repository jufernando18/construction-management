import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/core/request/request-util';
import { IBuildType } from '../build-type.model';

type EntityResponseType = HttpResponse<IBuildType>;
type EntityArrayResponseType = HttpResponse<IBuildType[]>;

@Injectable({ providedIn: 'root' })
export class BuildTypeService {
  public resourceUrl = SERVER_API_URL + 'api/build-types';

  constructor(protected http: HttpClient) {}

  create(buildType: IBuildType): Observable<EntityResponseType> {
    return this.http.post<IBuildType>(this.resourceUrl, buildType, { observe: 'response' });
  }

  update(buildType: IBuildType): Observable<EntityResponseType> {
    return this.http.put<IBuildType>(this.resourceUrl, buildType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBuildType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBuildType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
