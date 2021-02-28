import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuildOrder } from '../build-order.model';

@Component({
  selector: 'jhi-build-order-detail',
  templateUrl: './build-order-detail.component.html',
})
export class BuildOrderDetailComponent implements OnInit {
  buildOrder: IBuildOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildOrder }) => {
      this.buildOrder = buildOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
