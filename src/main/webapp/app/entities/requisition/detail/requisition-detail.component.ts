import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisition } from '../requisition.model';

@Component({
  selector: 'jhi-requisition-detail',
  templateUrl: './requisition-detail.component.html',
})
export class RequisitionDetailComponent implements OnInit {
  requisition: IRequisition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requisition }) => {
      this.requisition = requisition;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
