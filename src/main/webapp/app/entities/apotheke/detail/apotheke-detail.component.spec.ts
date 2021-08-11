import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApothekeDetailComponent } from './apotheke-detail.component';

describe('Component Tests', () => {
  describe('Apotheke Management Detail Component', () => {
    let comp: ApothekeDetailComponent;
    let fixture: ComponentFixture<ApothekeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ApothekeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ apotheke: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ApothekeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApothekeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load apotheke on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apotheke).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
