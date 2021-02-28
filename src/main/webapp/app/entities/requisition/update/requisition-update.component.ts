import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRequisition, Requisition } from '../requisition.model';
import { RequisitionService } from '../service/requisition.service';
import { IBuildType } from 'app/entities/build-type/build-type.model';
import { BuildTypeService } from 'app/entities/build-type/service/build-type.service';
import { ICitadel } from 'app/entities/citadel/citadel.model';
import { CitadelService } from 'app/entities/citadel/service/citadel.service';

@Component({
  selector: 'jhi-requisition-update',
  templateUrl: './requisition-update.component.html',
})
export class RequisitionUpdateComponent implements OnInit {
  isSaving = false;
  buildtypes: IBuildType[] = [];
  citadels: ICitadel[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    coordinate: [null, [Validators.required, Validators.pattern('^\\d+,\\d+$')]],
    state: [null, [Validators.required]],
    date: [null, [Validators.required]],
    buildType: [null, Validators.required],
    citadel: [null, Validators.required],
  });

  constructor(
    protected requisitionService: RequisitionService,
    protected buildTypeService: BuildTypeService,
    protected citadelService: CitadelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requisition }) => {
      this.updateForm(requisition);

      this.buildTypeService.query().subscribe((res: HttpResponse<IBuildType[]>) => (this.buildtypes = res.body ?? []));

      this.citadelService.query().subscribe((res: HttpResponse<ICitadel[]>) => (this.citadels = res.body ?? []));
    });
  }

  updateForm(requisition: IRequisition): void {
    this.editForm.patchValue({
      id: requisition.id,
      name: requisition.name,
      coordinate: requisition.coordinate,
      state: requisition.state,
      date: requisition.date,
      buildType: requisition.buildType,
      citadel: requisition.citadel,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requisition = this.createFromForm();
    if (requisition.id !== undefined) {
      this.subscribeToSaveResponse(this.requisitionService.update(requisition));
    } else {
      this.subscribeToSaveResponse(this.requisitionService.create(requisition));
    }
  }

  private createFromForm(): IRequisition {
    return {
      ...new Requisition(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      coordinate: this.editForm.get(['coordinate'])!.value,
      state: this.editForm.get(['state'])!.value,
      date: this.editForm.get(['date'])!.value,
      buildType: this.editForm.get(['buildType'])!.value,
      citadel: this.editForm.get(['citadel'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisition>>): void {
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

  trackBuildTypeById(index: number, item: IBuildType): number {
    return item.id!;
  }

  trackCitadelById(index: number, item: ICitadel): number {
    return item.id!;
  }
}
