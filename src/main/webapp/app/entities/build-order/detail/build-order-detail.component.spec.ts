import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildOrder } from '../build-order.model';

import { BuildOrderDetailComponent } from './build-order-detail.component';

describe('Component Tests', () => {
  describe('BuildOrder Management Detail Component', () => {
    let comp: BuildOrderDetailComponent;
    let fixture: ComponentFixture<BuildOrderDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BuildOrderDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ buildOrder: new BuildOrder(123) }) },
          },
        ],
      })
        .overrideTemplate(BuildOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BuildOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load buildOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.buildOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
