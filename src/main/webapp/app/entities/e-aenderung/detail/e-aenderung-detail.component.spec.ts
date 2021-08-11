import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EAenderungDetailComponent } from './e-aenderung-detail.component';

describe('Component Tests', () => {
  describe('EAenderung Management Detail Component', () => {
    let comp: EAenderungDetailComponent;
    let fixture: ComponentFixture<EAenderungDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EAenderungDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ eAenderung: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EAenderungDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EAenderungDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eAenderung on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eAenderung).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
