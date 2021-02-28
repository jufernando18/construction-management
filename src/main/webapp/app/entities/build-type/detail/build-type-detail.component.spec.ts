import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildType } from '../build-type.model';

import { BuildTypeDetailComponent } from './build-type-detail.component';

describe('Component Tests', () => {
  describe('BuildType Management Detail Component', () => {
    let comp: BuildTypeDetailComponent;
    let fixture: ComponentFixture<BuildTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BuildTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ buildType: new BuildType(123) }) },
          },
        ],
      })
        .overrideTemplate(BuildTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BuildTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load buildType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.buildType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
