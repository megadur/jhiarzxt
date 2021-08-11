import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Muster16AbrDetailComponent } from './muster-16-abr-detail.component';

describe('Component Tests', () => {
  describe('Muster16Abr Management Detail Component', () => {
    let comp: Muster16AbrDetailComponent;
    let fixture: ComponentFixture<Muster16AbrDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Muster16AbrDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ muster16Abr: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Muster16AbrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16AbrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load muster16Abr on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.muster16Abr).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
