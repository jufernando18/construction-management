import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Material } from '../material.model';

import { MaterialDetailComponent } from './material-detail.component';

describe('Component Tests', () => {
  describe('Material Management Detail Component', () => {
    let comp: MaterialDetailComponent;
    let fixture: ComponentFixture<MaterialDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MaterialDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ material: new Material(123) }) },
          },
        ],
      })
        .overrideTemplate(MaterialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaterialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load material on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.material).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
