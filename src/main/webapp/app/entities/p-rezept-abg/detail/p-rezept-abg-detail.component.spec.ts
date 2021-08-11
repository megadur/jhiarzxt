import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PRezeptAbgDetailComponent } from './p-rezept-abg-detail.component';

describe('Component Tests', () => {
  describe('PRezeptAbg Management Detail Component', () => {
    let comp: PRezeptAbgDetailComponent;
    let fixture: ComponentFixture<PRezeptAbgDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PRezeptAbgDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pRezeptAbg: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PRezeptAbgDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PRezeptAbgDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pRezeptAbg on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pRezeptAbg).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
