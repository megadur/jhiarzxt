import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMuster16Abr, Muster16Abr } from '../muster-16-abr.model';
import { Muster16AbrService } from '../service/muster-16-abr.service';

@Component({
  selector: 'jhi-muster-16-abr-update',
  templateUrl: './muster-16-abr-update.component.html',
})
export class Muster16AbrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    betragRabA: [],
    betragRabH: [],
    betragApoAusz: [],
  });

  constructor(protected muster16AbrService: Muster16AbrService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muster16Abr }) => {
      this.updateForm(muster16Abr);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const muster16Abr = this.createFromForm();
    if (muster16Abr.id !== undefined) {
      this.subscribeToSaveResponse(this.muster16AbrService.update(muster16Abr));
    } else {
      this.subscribeToSaveResponse(this.muster16AbrService.create(muster16Abr));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMuster16Abr>>): void {
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

  protected updateForm(muster16Abr: IMuster16Abr): void {
    this.editForm.patchValue({
      id: muster16Abr.id,
      betragRabA: muster16Abr.betragRabA,
      betragRabH: muster16Abr.betragRabH,
      betragApoAusz: muster16Abr.betragApoAusz,
    });
  }

  protected createFromForm(): IMuster16Abr {
    return {
      ...new Muster16Abr(),
      id: this.editForm.get(['id'])!.value,
      betragRabA: this.editForm.get(['betragRabA'])!.value,
      betragRabH: this.editForm.get(['betragRabH'])!.value,
      betragApoAusz: this.editForm.get(['betragApoAusz'])!.value,
    };
  }
}
