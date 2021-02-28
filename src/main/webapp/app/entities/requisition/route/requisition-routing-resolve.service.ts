import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequisition, Requisition } from '../requisition.model';
import { RequisitionService } from '../service/requisition.service';

@Injectable({ providedIn: 'root' })
export class RequisitionRoutingResolveService implements Resolve<IRequisition> {
  constructor(private service: RequisitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequisition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((requisition: HttpResponse<Requisition>) => {
          if (requisition.body) {
            return of(requisition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Requisition());
  }
}
