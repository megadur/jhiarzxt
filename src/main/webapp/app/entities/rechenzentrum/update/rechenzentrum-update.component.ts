import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRechenzentrum, Rechenzentrum } from '../rechenzentrum.model';
import { RechenzentrumService } from '../service/rechenzentrum.service';

@Component({
  selector: 'jhi-rechenzentrum-update',
  templateUrl: './rechenzentrum-update.component.html',
})
export class RechenzentrumUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iD: [],
    name: [],
  });

  constructor(protected rechenzentrumService: RechenzentrumService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rechenzentrum }) => {
      this.updateForm(rechenzentrum);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rechenzentrum = this.createFromForm();
    if (rechenzentrum.id !== undefined) {
      this.subscribeToSaveResponse(this.rechenzentrumService.update(rechenzentrum));
    } else {
      this.subscribeToSaveResponse(this.rechenzentrumService.create(rechenzentrum));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRechenzentrum>>): void {
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

  protected updateForm(rechenzentrum: IRechenzentrum): void {
    this.editForm.patchValue({
      id: rechenzentrum.id,
      iD: rechenzentrum.iD,
      name: rechenzentrum.name,
    });
  }

  protected createFromForm(): IRechenzentrum {
    return {
      ...new Rechenzentrum(),
      id: this.editForm.get(['id'])!.value,
      iD: this.editForm.get(['iD'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
