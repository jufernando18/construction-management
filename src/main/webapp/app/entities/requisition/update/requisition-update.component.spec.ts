jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RequisitionService } from '../service/requisition.service';
import { Requisition } from '../requisition.model';
import { BuildType } from 'app/entities/build-type/build-type.model';
import { Citadel } from 'app/entities/citadel/citadel.model';

import { RequisitionUpdateComponent } from './requisition-update.component';

describe('Component Tests', () => {
  describe('Requisition Management Update Component', () => {
    let comp: RequisitionUpdateComponent;
    let fixture: ComponentFixture<RequisitionUpdateComponent>;
    let service: RequisitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RequisitionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RequisitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RequisitionUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RequisitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Requisition(123);
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
        const entity = new Requisition();
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
      describe('trackBuildTypeById', () => {
        it('Should return tracked BuildType primary key', () => {
          const entity = new BuildType(123);
          const trackResult = comp.trackBuildTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCitadelById', () => {
        it('Should return tracked Citadel primary key', () => {
          const entity = new Citadel(123);
          const trackResult = comp.trackCitadelById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
