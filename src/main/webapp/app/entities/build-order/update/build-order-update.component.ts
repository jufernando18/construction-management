import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBuildOrder, BuildOrder } from '../build-order.model';
import { BuildOrderService } from '../service/build-order.service';
import { IRequisition } from 'app/entities/requisition/requisition.model';
import { RequisitionService } from 'app/entities/requisition/service/requisition.service';

@Component({
  selector: 'jhi-build-order-update',
  templateUrl: './build-order-update.component.html',
})
export class BuildOrderUpdateComponent implements OnInit {
  isSaving = false;
  requisitions: IRequisition[] = [];

  editForm = this.fb.group({
    id: [],
    state: [null, [Validators.required]],
    start: [null, [Validators.required]],
    finish: [null, [Validators.required]],
    requisition: [null, Validators.required],
  });

  constructor(
    protected buildOrderService: BuildOrderService,
    protected requisitionService: RequisitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildOrder }) => {
      this.updateForm(buildOrder);

      this.requisitionService
        .query({ 'buildOrderId.specified': 'false' })
        .pipe(map((res: HttpResponse<IRequisition[]>) => res.body ?? []))
        .subscribe((resBody: IRequisition[]) => {
          if (!buildOrder.requisition || !buildOrder.requisition.id) {
            this.requisitions = resBody;
          } else {
            this.requisitionService
              .find(buildOrder.requisition.id)
              .pipe(map((subRes: HttpResponse<IRequisition>) => (subRes.body ? [subRes.body].concat(resBody) : resBody)))
              .subscribe((concatRes: IRequisition[]) => (this.requisitions = concatRes));
          }
        });
    });
  }

  updateForm(buildOrder: IBuildOrder): void {
    this.editForm.patchValue({
      id: buildOrder.id,
      state: buildOrder.state,
      start: buildOrder.start,
      finish: buildOrder.finish,
      requisition: buildOrder.requisition,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const buildOrder = this.createFromForm();
    if (buildOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.buildOrderService.update(buildOrder));
    } else {
      this.subscribeToSaveResponse(this.buildOrderService.create(buildOrder));
    }
  }

  private createFromForm(): IBuildOrder {
    return {
      ...new BuildOrder(),
      id: this.editForm.get(['id'])!.value,
      state: this.editForm.get(['state'])!.value,
      start: this.editForm.get(['start'])!.value,
      finish: this.editForm.get(['finish'])!.value,
      requisition: this.editForm.get(['requisition'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuildOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackRequisitionById(index: number, item: IRequisition): number {
    return item.id!;
  }
}
