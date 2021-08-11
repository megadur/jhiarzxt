import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MArtikelService } from '../service/m-artikel.service';

import { MArtikelComponent } from './m-artikel.component';

describe('Component Tests', () => {
  describe('MArtikel Management Component', () => {
    let comp: MArtikelComponent;
    let fixture: ComponentFixture<MArtikelComponent>;
    let service: MArtikelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MArtikelComponent],
      })
        .overrideTemplate(MArtikelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MArtikelComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MArtikelService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mArtikels?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
