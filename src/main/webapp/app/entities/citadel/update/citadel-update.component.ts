import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICitadel, Citadel } from '../citadel.model';
import { CitadelService } from '../service/citadel.service';

@Component({
  selector: 'jhi-citadel-update',
  templateUrl: './citadel-update.component.html',
})
export class CitadelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    address: [null, [Validators.required]],
    start: [null, [Validators.required]],
    finish: [null, [Validators.required]],
  });

  constructor(protected citadelService: CitadelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citadel }) => {
      this.updateForm(citadel);
    });
  }

  updateForm(citadel: ICitadel): void {
    this.editForm.patchValue({
      id: citadel.id,
      name: citadel.name,
      address: citadel.address,
      start: citadel.start,
      finish: citadel.finish,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const citadel = this.createFromForm();
    if (citadel.id !== undefined) {
      this.subscribeToSaveResponse(this.citadelService.update(citadel));
    } else {
      this.subscribeToSaveResponse(this.citadelService.create(citadel));
    }
  }

  private createFromForm(): ICitadel {
    return {
      ...new Citadel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      address: this.editForm.get(['address'])!.value,
      start: this.editForm.get(['start'])!.value,
      finish: this.editForm.get(['finish'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICitadel>>): void {
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
}
