import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Muster16AbgDetailComponent } from './muster-16-abg-detail.component';

describe('Component Tests', () => {
  describe('Muster16Abg Management Detail Component', () => {
    let comp: Muster16AbgDetailComponent;
    let fixture: ComponentFixture<Muster16AbgDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [Muster16AbgDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ muster16Abg: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(Muster16AbgDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Muster16AbgDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load muster16Abg on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.muster16Abg).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
