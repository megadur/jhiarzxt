import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PRezeptVerDetailComponent } from './p-rezept-ver-detail.component';

describe('Component Tests', () => {
  describe('PRezeptVer Management Detail Component', () => {
    let comp: PRezeptVerDetailComponent;
    let fixture: ComponentFixture<PRezeptVerDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PRezeptVerDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pRezeptVer: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PRezeptVerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PRezeptVerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pRezeptVer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pRezeptVer).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
