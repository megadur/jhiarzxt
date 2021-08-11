import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILieferung, Lieferung } from '../lieferung.model';
import { LieferungService } from '../service/lieferung.service';
import { IApotheke } from 'app/entities/apotheke/apotheke.model';
import { ApothekeService } from 'app/entities/apotheke/service/apotheke.service';

@Component({
  selector: 'jhi-lieferung-update',
  templateUrl: './lieferung-update.component.html',
})
export class LieferungUpdateComponent implements OnInit {
  isSaving = false;

  apothekesSharedCollection: IApotheke[] = [];

  editForm = this.fb.group({
    id: [],
    iD: [],
    datum: [],
    apoIk: [],
    apotheke: [],
  });

  constructor(
    protected lieferungService: LieferungService,
    protected apothekeService: ApothekeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lieferung }) => {
      this.updateForm(lieferung);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lieferung = this.createFromForm();
    if (lieferung.id !== undefined) {
      this.subscribeToSaveResponse(this.lieferungService.update(lieferung));
    } else {
      this.subscribeToSaveResponse(this.lieferungService.create(lieferung));
    }
  }

  trackApothekeById(index: number, item: IApotheke): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILieferung>>): void {
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

  protected updateForm(lieferung: ILieferung): void {
    this.editForm.patchValue({
      id: lieferung.id,
      iD: lieferung.iD,
      datum: lieferung.datum,
      apoIk: lieferung.apoIk,
      apotheke: lieferung.apotheke,
    });

    this.apothekesSharedCollection = this.apothekeService.addApothekeToCollectionIfMissing(
      this.apothekesSharedCollection,
      lieferung.apotheke
    );
  }

  protected loadRelationshipsOptions(): void {
    this.apothekeService
      .query()
      .pipe(map((res: HttpResponse<IApotheke[]>) => res.body ?? []))
      .pipe(
        map((apothekes: IApotheke[]) =>
          this.apothekeService.addApothekeToCollectionIfMissing(apothekes, this.editForm.get('apotheke')!.value)
        )
      )
      .subscribe((apothekes: IApotheke[]) => (this.apothekesSharedCollection = apothekes));
  }

  protected createFromForm(): ILieferung {
    return {
      ...new Lieferung(),
      id: this.editForm.get(['id'])!.value,
      iD: this.editForm.get(['iD'])!.value,
      datum: this.editForm.get(['datum'])!.value,
      apoIk: this.editForm.get(['apoIk'])!.value,
      apotheke: this.editForm.get(['apotheke'])!.value,
    };
  }
}
