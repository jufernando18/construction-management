import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBuildType, BuildType } from '../build-type.model';
import { BuildTypeService } from '../service/build-type.service';
import { IMaterial } from 'app/entities/material/material.model';
import { MaterialService } from 'app/entities/material/service/material.service';

@Component({
  selector: 'jhi-build-type-update',
  templateUrl: './build-type-update.component.html',
})
export class BuildTypeUpdateComponent implements OnInit {
  isSaving = false;
  materials: IMaterial[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    duration: [null, [Validators.required, Validators.min(1)]],
    amountMaterial1: [null, [Validators.required, Validators.min(0)]],
    amountMaterial2: [null, [Validators.required, Validators.min(0)]],
    amountMaterial3: [null, [Validators.required, Validators.min(0)]],
    amountMaterial4: [null, [Validators.required, Validators.min(0)]],
    amountMaterial5: [null, [Validators.required, Validators.min(0)]],
    material1: [null, Validators.required],
    material2: [],
    material3: [],
    material4: [],
    material5: [],
  });

  constructor(
    protected buildTypeService: BuildTypeService,
    protected materialService: MaterialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buildType }) => {
      this.updateForm(buildType);

      this.materialService.query().subscribe((res: HttpResponse<IMaterial[]>) => (this.materials = res.body ?? []));
    });
  }

  updateForm(buildType: IBuildType): void {
    this.editForm.patchValue({
      id: buildType.id,
      name: buildType.name,
      duration: buildType.duration,
      amountMaterial1: buildType.amountMaterial1,
      amountMaterial2: buildType.amountMaterial2,
      amountMaterial3: buildType.amountMaterial3,
      amountMaterial4: buildType.amountMaterial4,
      amountMaterial5: buildType.amountMaterial5,
      material1: buildType.material1,
      material2: buildType.material2,
      material3: buildType.material3,
      material4: buildType.material4,
      material5: buildType.material5,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const buildType = this.createFromForm();
    if (buildType.id !== undefined) {
      this.subscribeToSaveResponse(this.buildTypeService.update(buildType));
    } else {
      this.subscribeToSaveResponse(this.buildTypeService.create(buildType));
    }
  }

  private createFromForm(): IBuildType {
    return {
      ...new BuildType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      amountMaterial1: this.editForm.get(['amountMaterial1'])!.value,
      amountMaterial2: this.editForm.get(['amountMaterial2'])!.value,
      amountMaterial3: this.editForm.get(['amountMaterial3'])!.value,
      amountMaterial4: this.editForm.get(['amountMaterial4'])!.value,
      amountMaterial5: this.editForm.get(['amountMaterial5'])!.value,
      material1: this.editForm.get(['material1'])!.value,
      material2: this.editForm.get(['material2'])!.value,
      material3: this.editForm.get(['material3'])!.value,
      material4: this.editForm.get(['material4'])!.value,
      material5: this.editForm.get(['material5'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuildType>>): void {
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

  trackMaterialById(index: number, item: IMaterial): number {
    return item.id!;
  }
}
