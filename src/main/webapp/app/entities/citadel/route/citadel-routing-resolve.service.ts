import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICitadel, Citadel } from '../citadel.model';
import { CitadelService } from '../service/citadel.service';

@Injectable({ providedIn: 'root' })
export class CitadelRoutingResolveService implements Resolve<ICitadel> {
  constructor(private service: CitadelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICitadel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((citadel: HttpResponse<Citadel>) => {
          if (citadel.body) {
            return of(citadel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Citadel());
  }
}
