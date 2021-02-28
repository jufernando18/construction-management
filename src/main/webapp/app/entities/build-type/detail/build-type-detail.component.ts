import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuildType } from '../build-type.model';

@Component({
  selector: 'jhi-build-type-detail',
  templateUrl: './build-type-detail.component.html',
})
export class BuildTypeDetailComponent implements OnInit {
  buildType: IBuildType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildType }) => {
      this.buildType = buildType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
