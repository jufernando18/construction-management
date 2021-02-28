jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildTypeService } from '../service/build-type.service';
import { BuildType } from '../build-type.model';
import { Material } from 'app/entities/material/material.model';

import { BuildTypeUpdateComponent } from './build-type-update.component';

describe('Component Tests', () => {
  describe('BuildType Management Update Component', () => {
    let comp: BuildTypeUpdateComponent;
    let fixture: ComponentFixture<BuildTypeUpdateComponent>;
    let service: BuildTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BuildTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BuildTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BuildTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BuildTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BuildType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BuildType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMaterialById', () => {
        it('Should return tracked Material primary key', () => {
          const entity = new Material(123);
          const trackResult = comp.trackMaterialById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
