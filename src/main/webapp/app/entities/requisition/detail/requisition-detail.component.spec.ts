import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Requisition } from '../requisition.model';

import { RequisitionDetailComponent } from './requisition-detail.component';

describe('Component Tests', () => {
  describe('Requisition Management Detail Component', () => {
    let comp: RequisitionDetailComponent;
    let fixture: ComponentFixture<RequisitionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RequisitionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ requisition: new Requisition(123) }) },
          },
        ],
      })
        .overrideTemplate(RequisitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequisitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load requisition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.requisition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
