import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBuildOrder, BuildOrder } from '../build-order.model';
import { BuildOrderService } from '../service/build-order.service';

@Injectable({ providedIn: 'root' })
export class BuildOrderRoutingResolveService implements Resolve<IBuildOrder> {
  constructor(private service: BuildOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuildOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((buildOrder: HttpResponse<BuildOrder>) => {
          if (buildOrder.body) {
            return of(buildOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BuildOrder());
  }
}
