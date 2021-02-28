jest.mock('@angular/router');

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildOrderService } from '../service/build-order.service';
import { BuildOrder } from '../build-order.model';
import { Requisition } from 'app/entities/requisition/requisition.model';

import { BuildOrderUpdateComponent } from './build-order-update.component';

describe('Component Tests', () => {
  describe('BuildOrder Management Update Component', () => {
    let comp: BuildOrderUpdateComponent;
    let fixture: ComponentFixture<BuildOrderUpdateComponent>;
    let service: BuildOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BuildOrderUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BuildOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BuildOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BuildOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BuildOrder(123);
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
        const entity = new BuildOrder();
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
      describe('trackRequisitionById', () => {
        it('Should return tracked Requisition primary key', () => {
          const entity = new Requisition(123);
          const trackResult = comp.trackRequisitionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
