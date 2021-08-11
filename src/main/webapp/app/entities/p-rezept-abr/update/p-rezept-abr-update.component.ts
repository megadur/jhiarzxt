import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPRezeptAbr, PRezeptAbr } from '../p-rezept-abr.model';
import { PRezeptAbrService } from '../service/p-rezept-abr.service';

@Component({
  selector: 'jhi-p-rezept-abr-update',
  templateUrl: './p-rezept-abr-update.component.html',
})
export class PRezeptAbrUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pRezeptId: [],
    betragRabA: [],
    betragRabH: [],
    betragApoAusz: [],
  });

  constructor(protected pRezeptAbrService: PRezeptAbrService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pRezeptAbr }) => {
      this.updateForm(pRezeptAbr);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pRezeptAbr = this.createFromForm();
    if (pRezeptAbr.id !== undefined) {
      this.subscribeToSaveResponse(this.pRezeptAbrService.update(pRezeptAbr));
    } else {
      this.subscribeToSaveResponse(this.pRezeptAbrService.create(pRezeptAbr));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPRezeptAbr>>): void {
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

  protected updateForm(pRezeptAbr: IPRezeptAbr): void {
    this.editForm.patchValue({
      id: pRezeptAbr.id,
      pRezeptId: pRezeptAbr.pRezeptId,
      betragRabA: pRezeptAbr.betragRabA,
      betragRabH: pRezeptAbr.betragRabH,
      betragApoAusz: pRezeptAbr.betragApoAusz,
    });
  }

  protected createFromForm(): IPRezeptAbr {
    return {
      ...new PRezeptAbr(),
      id: this.editForm.get(['id'])!.value,
      pRezeptId: this.editForm.get(['pRezeptId'])!.value,
      betragRabA: this.editForm.get(['betragRabA'])!.value,
      betragRabH: this.editForm.get(['betragRabH'])!.value,
      betragApoAusz: this.editForm.get(['betragApoAusz'])!.value,
    };
  }
}
