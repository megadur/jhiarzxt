import { IMuster16Abg } from 'app/entities/muster-16-abg/muster-16-abg.model';

export interface IMuster16Abr {
  id?: number;
  betragRabA?: string | null;
  betragRabH?: string | null;
  betragApoAusz?: string | null;
  mRezeptId?: IMuster16Abg | null;
}

export class Muster16Abr implements IMuster16Abr {
  constructor(
    public id?: number,
    public betragRabA?: string | null,
    public betragRabH?: string | null,
    public betragApoAusz?: string | null,
    public mRezeptId?: IMuster16Abg | null
  ) {}
}

export function getMuster16AbrIdentifier(muster16Abr: IMuster16Abr): number | undefined {
  return muster16Abr.id;
}
