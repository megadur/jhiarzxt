import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPRezeptVer, PRezeptVer } from '../p-rezept-ver.model';
import { PRezeptVerService } from '../service/p-rezept-ver.service';
import { IPRezeptAbg } from 'app/entities/p-rezept-abg/p-rezept-abg.model';
import { PRezeptAbgService } from 'app/entities/p-rezept-abg/service/p-rezept-abg.service';

@Component({
  selector: 'jhi-p-rezept-ver-update',
  templateUrl: './p-rezept-ver-update.component.html',
})
export class PRezeptVerUpdateComponent implements OnInit {
  isSaving = false;

  pRezeptIdsCollection: IPRezeptAbg[] = [];

  editForm = this.fb.group({
    id: [],
    pRezeptId9: [],
    apoIk: [],
    apoIkSend: [],
    arbPlatz: [],
    avsId: [],
    bediener: [],
    bvg: [],
    eStatus: [],
    erstZeitpunkt: [],
    kName: [],
    kkIk: [],
    laNr: [],
    vrsNr: [],
    vrtrgsArztNr: [],
    vGeb: [],
    vStat: [],
    pRezeptId: [],
  });

  constructor(
    protected pRezeptVerService: PRezeptVerService,
    protected pRezeptAbgService: PRezeptAbgService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptVer }) => {
      this.updateForm(pRezeptVer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pRezeptVer = this.createFromForm();
    if (pRezeptVer.id !== undefined) {
      this.subscribeToSaveResponse(this.pRezeptVerService.update(pRezeptVer));
    } else {
      this.subscribeToSaveResponse(this.pRezeptVerService.create(pRezeptVer));
    }
  }

  trackPRezeptAbgById(index: number, item: IPRezeptAbg): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPRezeptVer>>): void {
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

  protected updateForm(pRezeptVer: IPRezeptVer): void {
    this.editForm.patchValue({
      id: pRezeptVer.id,
      pRezeptId9: pRezeptVer.pRezeptId9,
      apoIk: pRezeptVer.apoIk,
      apoIkSend: pRezeptVer.apoIkSend,
      arbPlatz: pRezeptVer.arbPlatz,
      avsId: pRezeptVer.avsId,
      bediener: pRezeptVer.bediener,
      bvg: pRezeptVer.bvg,
      eStatus: pRezeptVer.eStatus,
      erstZeitpunkt: pRezeptVer.erstZeitpunkt,
      kName: pRezeptVer.kName,
      kkIk: pRezeptVer.kkIk,
      laNr: pRezeptVer.laNr,
      vrsNr: pRezeptVer.vrsNr,
      vrtrgsArztNr: pRezeptVer.vrtrgsArztNr,
      vGeb: pRezeptVer.vGeb,
      vStat: pRezeptVer.vStat,
      pRezeptId: pRezeptVer.pRezeptId,
    });

    this.pRezeptIdsCollection = this.pRezeptAbgService.addPRezeptAbgToCollectionIfMissing(this.pRezeptIdsCollection, pRezeptVer.pRezeptId);
  }

  protected loadRelationshipsOptions(): void {
    this.pRezeptAbgService
      .query({ filter: 'prezeptid-is-null' })
      .pipe(map((res: HttpResponse<IPRezeptAbg[]>) => res.body ?? []))
      .pipe(
        map((pRezeptAbgs: IPRezeptAbg[]) =>
          this.pRezeptAbgService.addPRezeptAbgToCollectionIfMissing(pRezeptAbgs, this.editForm.get('pRezeptId')!.value)
        )
      )
      .subscribe((pRezeptAbgs: IPRezeptAbg[]) => (this.pRezeptIdsCollection = pRezeptAbgs));
  }

  protected createFromForm(): IPRezeptVer {
    return {
      ...new PRezeptVer(),
      id: this.editForm.get(['id'])!.value,
      pRezeptId9: this.editForm.get(['pRezeptId9'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      apoIkSend: this.editForm.get(['apoIkSend'])!.value,
      arbPlatz: this.editForm.get(['arbPlatz'])!.value,
      avsId: this.editForm.get(['avsId'])!.value,
      bediener: this.editForm.get(['bediener'])!.value,
      bvg: this.editForm.get(['bvg'])!.value,
      eStatus: this.editForm.get(['eStatus'])!.value,
      erstZeitpunkt: this.editForm.get(['erstZeitpunkt'])!.value,
      kName: this.editForm.get(['kName'])!.value,
      kkIk: this.editForm.get(['kkIk'])!.value,
      laNr: this.editForm.get(['laNr'])!.value,
      vrsNr: this.editForm.get(['vrsNr'])!.value,
      vrtrgsArztNr: this.editForm.get(['vrtrgsArztNr'])!.value,
      vGeb: this.editForm.get(['vGeb'])!.value,
      vStat: this.editForm.get(['vStat'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
    };
  }
}
