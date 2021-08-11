import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMuster16Ver, Muster16Ver } from '../muster-16-ver.model';
import { Muster16VerService } from '../service/muster-16-ver.service';
import { IMuster16Abg } from 'app/entities/muster-16-abg/muster-16-abg.model';
import { Muster16AbgService } from 'app/entities/muster-16-abg/service/muster-16-abg.service';

@Component({
  selector: 'jhi-muster-16-ver-update',
  templateUrl: './muster-16-ver-update.component.html',
})
export class Muster16VerUpdateComponent implements OnInit {
  isSaving = false;

  mRezeptIdsCollection: IMuster16Abg[] = [];

  editForm = this.fb.group({
    id: [],
    aUnfall: [],
    abDatum: [],
    vrsNr: [],
    vrtrgsArztNr: [],
    sprStBedarf: [],
    unfall: [],
    unfallTag: [],
    vGeb: [],
    vStat: [],
    verDat: [],
    kName: [],
    kkIk: [],
    laNr: [],
    noctu: [],
    hilf: [],
    impf: [],
    kArt: [],
    rTyp: [],
    rezeptTyp: [],
    bgrPfl: [],
    bvg: [],
    eigBet: [],
    gebFrei: [],
    sonstige: [],
    vkGueltigBis: [],
    mRezeptId: [],
  });

  constructor(
    protected muster16VerService: Muster16VerService,
    protected muster16AbgService: Muster16AbgService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Ver }) => {
      this.updateForm(muster16Ver);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const muster16Ver = this.createFromForm();
    if (muster16Ver.id !== undefined) {
      this.subscribeToSaveResponse(this.muster16VerService.update(muster16Ver));
    } else {
      this.subscribeToSaveResponse(this.muster16VerService.create(muster16Ver));
    }
  }

  trackMuster16AbgById(index: number, item: IMuster16Abg): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMuster16Ver>>): void {
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

  protected updateForm(muster16Ver: IMuster16Ver): void {
    this.editForm.patchValue({
      id: muster16Ver.id,
      aUnfall: muster16Ver.aUnfall,
      abDatum: muster16Ver.abDatum,
      vrsNr: muster16Ver.vrsNr,
      vrtrgsArztNr: muster16Ver.vrtrgsArztNr,
      sprStBedarf: muster16Ver.sprStBedarf,
      unfall: muster16Ver.unfall,
      unfallTag: muster16Ver.unfallTag,
      vGeb: muster16Ver.vGeb,
      vStat: muster16Ver.vStat,
      verDat: muster16Ver.verDat,
      kName: muster16Ver.kName,
      kkIk: muster16Ver.kkIk,
      laNr: muster16Ver.laNr,
      noctu: muster16Ver.noctu,
      hilf: muster16Ver.hilf,
      impf: muster16Ver.impf,
      kArt: muster16Ver.kArt,
      rTyp: muster16Ver.rTyp,
      rezeptTyp: muster16Ver.rezeptTyp,
      bgrPfl: muster16Ver.bgrPfl,
      bvg: muster16Ver.bvg,
      eigBet: muster16Ver.eigBet,
      gebFrei: muster16Ver.gebFrei,
      sonstige: muster16Ver.sonstige,
      vkGueltigBis: muster16Ver.vkGueltigBis,
      mRezeptId: muster16Ver.mRezeptId,
    });

    this.mRezeptIdsCollection = this.muster16AbgService.addMuster16AbgToCollectionIfMissing(
      this.mRezeptIdsCollection,
      muster16Ver.mRezeptId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.muster16AbgService
      .query({ filter: 'mrezeptid-is-null' })
      .pipe(map((res: HttpResponse<IMuster16Abg[]>) => res.body ?? []))
      .pipe(
        map((muster16Abgs: IMuster16Abg[]) =>
          this.muster16AbgService.addMuster16AbgToCollectionIfMissing(muster16Abgs, this.editForm.get('mRezeptId')!.value)
        )
      )
      .subscribe((muster16Abgs: IMuster16Abg[]) => (this.mRezeptIdsCollection = muster16Abgs));
  }

  protected createFromForm(): IMuster16Ver {
    return {
      ...new Muster16Ver(),
      id: this.editForm.get(['id'])!.value,
      aUnfall: this.editForm.get(['aUnfall'])!.value,
      abDatum: this.editForm.get(['abDatum'])!.value,
      vrsNr: this.editForm.get(['vrsNr'])!.value,
      vrtrgsArztNr: this.editForm.get(['vrtrgsArztNr'])!.value,
      sprStBedarf: this.editForm.get(['sprStBedarf'])!.value,
      unfall: this.editForm.get(['unfall'])!.value,
      unfallTag: this.editForm.get(['unfallTag'])!.value,
      vGeb: this.editForm.get(['vGeb'])!.value,
      vStat: this.editForm.get(['vStat'])!.value,
      verDat: this.editForm.get(['verDat'])!.value,
      kName: this.editForm.get(['kName'])!.value,
      kkIk: this.editForm.get(['kkIk'])!.value,
      laNr: this.editForm.get(['laNr'])!.value,
      noctu: this.editForm.get(['noctu'])!.value,
      hilf: this.editForm.get(['hilf'])!.value,
      impf: this.editForm.get(['impf'])!.value,
      kArt: this.editForm.get(['kArt'])!.value,
      rTyp: this.editForm.get(['rTyp'])!.value,
      rezeptTyp: this.editForm.get(['rezeptTyp'])!.value,
      bgrPfl: this.editForm.get(['bgrPfl'])!.value,
      bvg: this.editForm.get(['bvg'])!.value,
      eigBet: this.editForm.get(['eigBet'])!.value,
      gebFrei: this.editForm.get(['gebFrei'])!.value,
      sonstige: this.editForm.get(['sonstige'])!.value,
      vkGueltigBis: this.editForm.get(['vkGueltigBis'])!.value,
      mRezeptId: this.editForm.get(['mRezeptId'])!.value,
    };
  }
}
