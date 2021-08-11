import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ERezeptDetailComponent } from './e-rezept-detail.component';

describe('Component Tests', () => {
  describe('ERezept Management Detail Component', () => {
    let comp: ERezeptDetailComponent;
    let fixture: ComponentFixture<ERezeptDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ERezeptDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ eRezept: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ERezeptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ERezeptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eRezept on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eRezept).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
