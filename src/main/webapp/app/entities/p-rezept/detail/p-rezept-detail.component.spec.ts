import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PRezeptDetailComponent } from './p-rezept-detail.component';

describe('Component Tests', () => {
  describe('PRezept Management Detail Component', () => {
    let comp: PRezeptDetailComponent;
    let fixture: ComponentFixture<PRezeptDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PRezeptDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pRezept: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PRezeptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PRezeptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pRezept on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pRezept).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
