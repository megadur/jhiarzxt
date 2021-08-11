import { IMuster16Abr } from 'app/entities/muster-16-abr/muster-16-abr.model';
import { IMuster16Ver } from 'app/entities/muster-16-ver/muster-16-ver.model';

export interface IMuster16Abg {
  id?: number;
  apoIk?: string | null;
  lieferDat?: string | null;
  aPeriode?: string | null;
  arbPlatz?: string | null;
  avsId?: string | null;
  bediener?: string | null;
  zuzahlung?: string | null;
  gesBrutto?: string | null;
  mRezeptId?: IMuster16Abr | null;
  mRezeptId?: IMuster16Ver | null;
}

export class Muster16Abg implements IMuster16Abg {
  constructor(
    public id?: number,
    public apoIk?: string | null,
    public lieferDat?: string | null,
    public aPeriode?: string | null,
    public arbPlatz?: string | null,
    public avsId?: string | null,
    public bediener?: string | null,
    public zuzahlung?: string | null,
    public gesBrutto?: string | null,
    public mRezeptId?: IMuster16Abr | null,
    public mRezeptId?: IMuster16Ver | null
  ) {}
}

export function getMuster16AbgIdentifier(muster16Abg: IMuster16Abg): number | undefined {
  return muster16Abg.id;
}
