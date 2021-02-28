import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBuildType, BuildType } from '../build-type.model';
import { BuildTypeService } from '../service/build-type.service';

@Injectable({ providedIn: 'root' })
export class BuildTypeRoutingResolveService implements Resolve<IBuildType> {
  constructor(private service: BuildTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBuildType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((buildType: HttpResponse<BuildType>) => {
          if (buildType.body) {
            return of(buildType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BuildType());
  }
}
