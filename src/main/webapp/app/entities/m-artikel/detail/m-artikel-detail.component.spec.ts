import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MArtikelDetailComponent } from './m-artikel-detail.component';

describe('Component Tests', () => {
  describe('MArtikel Management Detail Component', () => {
    let comp: MArtikelDetailComponent;
    let fixture: ComponentFixture<MArtikelDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MArtikelDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ mArtikel: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MArtikelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MArtikelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mArtikel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mArtikel).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
