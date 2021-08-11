import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IApotheke, Apotheke } from '../apotheke.model';
import { ApothekeService } from '../service/apotheke.service';
import { IRechenzentrum } from 'app/entities/rechenzentrum/rechenzentrum.model';
import { RechenzentrumService } from 'app/entities/rechenzentrum/service/rechenzentrum.service';

@Component({
  selector: 'jhi-apotheke-update',
  templateUrl: './apotheke-update.component.html',
})
export class ApothekeUpdateComponent implements OnInit {
  isSaving = false;

  rechenzentrumsSharedCollection: IRechenzentrum[] = [];

  editForm = this.fb.group({
    id: [],
    iK: [],
    inhaber: [],
    countryCode: [],
    plz: [],
    ort: [],
    str: [],
    hausNr: [],
    addrZusatz: [],
    rechenzentrums: [],
  });

  constructor(
    protected apothekeService: ApothekeService,
    protected rechenzentrumService: RechenzentrumService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apotheke }) => {
      this.updateForm(apotheke);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apotheke = this.createFromForm();
    if (apotheke.id !== undefined) {
      this.subscribeToSaveResponse(this.apothekeService.update(apotheke));
    } else {
      this.subscribeToSaveResponse(this.apothekeService.create(apotheke));
    }
  }

  trackRechenzentrumById(index: number, item: IRechenzentrum): number {
    return item.id!;
  }

  getSelectedRechenzentrum(option: IRechenzentrum, selectedVals?: IRechenzentrum[]): IRechenzentrum {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApotheke>>): void {
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

  protected updateForm(apotheke: IApotheke): void {
    this.editForm.patchValue({
      id: apotheke.id,
      iK: apotheke.iK,
      inhaber: apotheke.inhaber,
      countryCode: apotheke.countryCode,
      plz: apotheke.plz,
      ort: apotheke.ort,
      str: apotheke.str,
      hausNr: apotheke.hausNr,
      addrZusatz: apotheke.addrZusatz,
      rechenzentrums: apotheke.rechenzentrums,
    });

    this.rechenzentrumsSharedCollection = this.rechenzentrumService.addRechenzentrumToCollectionIfMissing(
      this.rechenzentrumsSharedCollection,
      ...(apotheke.rechenzentrums ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rechenzentrumService
      .query()
      .pipe(map((res: HttpResponse<IRechenzentrum[]>) => res.body ?? []))
      .pipe(
        map((rechenzentrums: IRechenzentrum[]) =>
          this.rechenzentrumService.addRechenzentrumToCollectionIfMissing(
            rechenzentrums,
            ...(this.editForm.get('rechenzentrums')!.value ?? [])
          )
        )
      )
      .subscribe((rechenzentrums: IRechenzentrum[]) => (this.rechenzentrumsSharedCollection = rechenzentrums));
  }

  protected createFromForm(): IApotheke {
    return {
      ...new Apotheke(),
      id: this.editForm.get(['id'])!.value,
      iK: this.editForm.get(['iK'])!.value,
      inhaber: this.editForm.get(['inhaber'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      plz: this.editForm.get(['plz'])!.value,
      ort: this.editForm.get(['ort'])!.value,
      str: this.editForm.get(['str'])!.value,
      hausNr: this.editForm.get(['hausNr'])!.value,
      addrZusatz: this.editForm.get(['addrZusatz'])!.value,
      rechenzentrums: this.editForm.get(['rechenzentrums'])!.value,
    };
  }
}
