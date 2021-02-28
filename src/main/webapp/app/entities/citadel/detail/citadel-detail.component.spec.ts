import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Citadel } from '../citadel.model';

import { CitadelDetailComponent } from './citadel-detail.component';

describe('Component Tests', () => {
  describe('Citadel Management Detail Component', () => {
    let comp: CitadelDetailComponent;
    let fixture: ComponentFixture<CitadelDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CitadelDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ citadel: new Citadel(123) }) },
          },
        ],
      })
        .overrideTemplate(CitadelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CitadelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load citadel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.citadel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
