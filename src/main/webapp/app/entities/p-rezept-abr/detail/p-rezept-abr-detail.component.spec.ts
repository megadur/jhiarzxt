import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PRezeptAbrDetailComponent } from './p-rezept-abr-detail.component';

describe('Component Tests', () => {
  describe('PRezeptAbr Management Detail Component', () => {
    let comp: PRezeptAbrDetailComponent;
    let fixture: ComponentFixture<PRezeptAbrDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PRezeptAbrDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pRezeptAbr: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PRezeptAbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PRezeptAbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pRezeptAbr on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pRezeptAbr).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
