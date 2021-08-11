import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMArtikel, MArtikel } from '../m-artikel.model';
import { MArtikelService } from '../service/m-artikel.service';
import { IMuster16 } from 'app/entities/muster-16/muster-16.model';
import { Muster16Service } from 'app/entities/muster-16/service/muster-16.service';

@Component({
  selector: 'jhi-m-artikel-update',
  templateUrl: './m-artikel-update.component.html',
})
export class MArtikelUpdateComponent implements OnInit {
  isSaving = false;

  muster16sSharedCollection: IMuster16[] = [];

  editForm = this.fb.group({
    id: [],
    mArtikelId: [],
    mRezeptId: [],
    apoIk: [],
    autidem: [],
    faktor: [],
    hilfsmittelNr: [],
    muster16Id: [],
    posNr: [],
    pzn: [],
    taxe: [],
    vZeile: [],
    muster16: [],
  });

  constructor(
    protected mArtikelService: MArtikelService,
    protected muster16Service: Muster16Service,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mArtikel }) => {
      this.updateForm(mArtikel);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mArtikel = this.createFromForm();
    if (mArtikel.id !== undefined) {
      this.subscribeToSaveResponse(this.mArtikelService.update(mArtikel));
    } else {
      this.subscribeToSaveResponse(this.mArtikelService.create(mArtikel));
    }
  }

  trackMuster16ById(index: number, item: IMuster16): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMArtikel>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mArtikel: IMArtikel): void {
    this.editForm.patchValue({
      id: mArtikel.id,
      mArtikelId: mArtikel.mArtikelId,
      mRezeptId: mArtikel.mRezeptId,
      apoIk: mArtikel.apoIk,
      autidem: mArtikel.autidem,
      faktor: mArtikel.faktor,
      hilfsmittelNr: mArtikel.hilfsmittelNr,
      muster16Id: mArtikel.muster16Id,
      posNr: mArtikel.posNr,
      pzn: mArtikel.pzn,
      taxe: mArtikel.taxe,
      vZeile: mArtikel.vZeile,
      muster16: mArtikel.muster16,
    });

    this.muster16sSharedCollection = this.muster16Service.addMuster16ToCollectionIfMissing(
      this.muster16sSharedCollection,
      mArtikel.muster16
    );
  }

  protected loadRelationshipsOptions(): void {
    this.muster16Service
      .query()
      .pipe(map((res: HttpResponse<IMuster16[]>) => res.body ?? []))
      .pipe(
        map((muster16s: IMuster16[]) =>
          this.muster16Service.addMuster16ToCollectionIfMissing(muster16s, this.editForm.get('muster16')!.value)
        )
      )
      .subscribe((muster16s: IMuster16[]) => (this.muster16sSharedCollection = muster16s));
  }

  protected createFromForm(): IMArtikel {
    return {
      ...new MArtikel(),
      id: this.editForm.get(['id'])!.value,
      mArtikelId: this.editForm.get(['mArtikelId'])!.value,
      mRezeptId: this.editForm.get(['mRezeptId'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      autidem: this.editForm.get(['autidem'])!.value,
      faktor: this.editForm.get(['faktor'])!.value,
      hilfsmittelNr: this.editForm.get(['hilfsmittelNr'])!.value,
      muster16Id: this.editForm.get(['muster16Id'])!.value,
      posNr: this.editForm.get(['posNr'])!.value,
      pzn: this.editForm.get(['pzn'])!.value,
      taxe: this.editForm.get(['taxe'])!.value,
      vZeile: this.editForm.get(['vZeile'])!.value,
      muster16: this.editForm.get(['muster16'])!.value,
    };
  }
}
