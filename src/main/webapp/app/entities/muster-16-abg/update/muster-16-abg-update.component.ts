import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMuster16Abg, Muster16Abg } from '../muster-16-abg.model';
import { Muster16AbgService } from '../service/muster-16-abg.service';
import { IMuster16Abr } from 'app/entities/muster-16-abr/muster-16-abr.model';
import { Muster16AbrService } from 'app/entities/muster-16-abr/service/muster-16-abr.service';

@Component({
  selector: 'jhi-muster-16-abg-update',
  templateUrl: './muster-16-abg-update.component.html',
})
export class Muster16AbgUpdateComponent implements OnInit {
  isSaving = false;

  mRezeptIdsCollection: IMuster16Abr[] = [];

  editForm = this.fb.group({
    id: [],
    apoIk: [],
    lieferDat: [],
    aPeriode: [],
    arbPlatz: [],
    avsId: [],
    bediener: [],
    zuzahlung: [],
    gesBrutto: [],
    mRezeptId: [],
  });

  constructor(
    protected muster16AbgService: Muster16AbgService,
    protected muster16AbrService: Muster16AbrService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Abg }) => {
      this.updateForm(muster16Abg);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const muster16Abg = this.createFromForm();
    if (muster16Abg.id !== undefined) {
      this.subscribeToSaveResponse(this.muster16AbgService.update(muster16Abg));
    } else {
      this.subscribeToSaveResponse(this.muster16AbgService.create(muster16Abg));
    }
  }

  trackMuster16AbrById(index: number, item: IMuster16Abr): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMuster16Abg>>): void {
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

  protected updateForm(muster16Abg: IMuster16Abg): void {
    this.editForm.patchValue({
      id: muster16Abg.id,
      apoIk: muster16Abg.apoIk,
      lieferDat: muster16Abg.lieferDat,
      aPeriode: muster16Abg.aPeriode,
      arbPlatz: muster16Abg.arbPlatz,
      avsId: muster16Abg.avsId,
      bediener: muster16Abg.bediener,
      zuzahlung: muster16Abg.zuzahlung,
      gesBrutto: muster16Abg.gesBrutto,
      mRezeptId: muster16Abg.mRezeptId,
    });

    this.mRezeptIdsCollection = this.muster16AbrService.addMuster16AbrToCollectionIfMissing(
      this.mRezeptIdsCollection,
      muster16Abg.mRezeptId
    );
  }

  protected loadRelationshipsOptions(): void {
    this.muster16AbrService
      .query({ filter: 'mrezeptid-is-null' })
      .pipe(map((res: HttpResponse<IMuster16Abr[]>) => res.body ?? []))
      .pipe(
        map((muster16Abrs: IMuster16Abr[]) =>
          this.muster16AbrService.addMuster16AbrToCollectionIfMissing(muster16Abrs, this.editForm.get('mRezeptId')!.value)
        )
      )
      .subscribe((muster16Abrs: IMuster16Abr[]) => (this.mRezeptIdsCollection = muster16Abrs));
  }

  protected createFromForm(): IMuster16Abg {
    return {
      ...new Muster16Abg(),
      id: this.editForm.get(['id'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      lieferDat: this.editForm.get(['lieferDat'])!.value,
      aPeriode: this.editForm.get(['aPeriode'])!.value,
      arbPlatz: this.editForm.get(['arbPlatz'])!.value,
      avsId: this.editForm.get(['avsId'])!.value,
      bediener: this.editForm.get(['bediener'])!.value,
      zuzahlung: this.editForm.get(['zuzahlung'])!.value,
      gesBrutto: this.editForm.get(['gesBrutto'])!.value,
      mRezeptId: this.editForm.get(['mRezeptId'])!.value,
    };
  }
}
