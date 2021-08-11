import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPRezeptAbg, PRezeptAbg } from '../p-rezept-abg.model';
import { PRezeptAbgService } from '../service/p-rezept-abg.service';
import { IPRezeptAbr } from 'app/entities/p-rezept-abr/p-rezept-abr.model';
import { PRezeptAbrService } from 'app/entities/p-rezept-abr/service/p-rezept-abr.service';

@Component({
  selector: 'jhi-p-rezept-abg-update',
  templateUrl: './p-rezept-abg-update.component.html',
})
export class PRezeptAbgUpdateComponent implements OnInit {
  isSaving = false;

  pRezeptIdsCollection: IPRezeptAbr[] = [];

  editForm = this.fb.group({
    id: [],
    pRezeptId: [],
    gebFrei: [],
    gesBrutto: [],
    hashCode: [],
    kArt: [],
    noctu: [],
    pRezeptTyp: [],
    rTyp: [],
    sonstige: [],
    sprStBedarf: [],
    verDat: [],
    vkGueltigBis: [],
    zuzahlung: [],
    pRezeptId: [],
  });

  constructor(
    protected pRezeptAbgService: PRezeptAbgService,
    protected pRezeptAbrService: PRezeptAbrService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptAbg }) => {
      this.updateForm(pRezeptAbg);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pRezeptAbg = this.createFromForm();
    if (pRezeptAbg.id !== undefined) {
      this.subscribeToSaveResponse(this.pRezeptAbgService.update(pRezeptAbg));
    } else {
      this.subscribeToSaveResponse(this.pRezeptAbgService.create(pRezeptAbg));
    }
  }

  trackPRezeptAbrById(index: number, item: IPRezeptAbr): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPRezeptAbg>>): void {
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

  protected updateForm(pRezeptAbg: IPRezeptAbg): void {
    this.editForm.patchValue({
      id: pRezeptAbg.id,
      pRezeptId: pRezeptAbg.pRezeptId,
      gebFrei: pRezeptAbg.gebFrei,
      gesBrutto: pRezeptAbg.gesBrutto,
      hashCode: pRezeptAbg.hashCode,
      kArt: pRezeptAbg.kArt,
      noctu: pRezeptAbg.noctu,
      pRezeptTyp: pRezeptAbg.pRezeptTyp,
      rTyp: pRezeptAbg.rTyp,
      sonstige: pRezeptAbg.sonstige,
      sprStBedarf: pRezeptAbg.sprStBedarf,
      verDat: pRezeptAbg.verDat,
      vkGueltigBis: pRezeptAbg.vkGueltigBis,
      zuzahlung: pRezeptAbg.zuzahlung,
      pRezeptId: pRezeptAbg.pRezeptId,
    });

    this.pRezeptIdsCollection = this.pRezeptAbrService.addPRezeptAbrToCollectionIfMissing(this.pRezeptIdsCollection, pRezeptAbg.pRezeptId);
  }

  protected loadRelationshipsOptions(): void {
    this.pRezeptAbrService
      .query({ filter: 'prezeptid-is-null' })
      .pipe(map((res: HttpResponse<IPRezeptAbr[]>) => res.body ?? []))
      .pipe(
        map((pRezeptAbrs: IPRezeptAbr[]) =>
          this.pRezeptAbrService.addPRezeptAbrToCollectionIfMissing(pRezeptAbrs, this.editForm.get('pRezeptId')!.value)
        )
      )
      .subscribe((pRezeptAbrs: IPRezeptAbr[]) => (this.pRezeptIdsCollection = pRezeptAbrs));
  }

  protected createFromForm(): IPRezeptAbg {
    return {
      ...new PRezeptAbg(),
      id: this.editForm.get(['id'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      gebFrei: this.editForm.get(['gebFrei'])!.value,
      gesBrutto: this.editForm.get(['gesBrutto'])!.value,
      hashCode: this.editForm.get(['hashCode'])!.value,
      kArt: this.editForm.get(['kArt'])!.value,
      noctu: this.editForm.get(['noctu'])!.value,
      pRezeptTyp: this.editForm.get(['pRezeptTyp'])!.value,
      rTyp: this.editForm.get(['rTyp'])!.value,
      sonstige: this.editForm.get(['sonstige'])!.value,
      sprStBedarf: this.editForm.get(['sprStBedarf'])!.value,
      verDat: this.editForm.get(['verDat'])!.value,
      vkGueltigBis: this.editForm.get(['vkGueltigBis'])!.value,
      zuzahlung: this.editForm.get(['zuzahlung'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
    };
  }
}
