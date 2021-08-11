import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Muster16VerDetailComponent } from './muster-16-ver-detail.component';

describe('Component Tests', () => {
  describe('Muster16Ver Management Detail Component', () => {
    let comp: Muster16VerDetailComponent;
    let fixture: ComponentFixture<Muster16VerDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Muster16VerDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ muster16Ver: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Muster16VerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16VerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load muster16Ver on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.muster16Ver).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
