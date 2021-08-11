import { IPRezeptAbr } from 'app/entities/p-rezept-abr/p-rezept-abr.model';
import { IPRezeptVer } from 'app/entities/p-rezept-ver/p-rezept-ver.model';

export interface IPRezeptAbg {
  id?: number;
  pRezeptId?: string | null;
  gebFrei?: string | null;
  gesBrutto?: string | null;
  hashCode?: string | null;
  kArt?: string | null;
  noctu?: string | null;
  pRezeptTyp?: string | null;
  rTyp?: string | null;
  sonstige?: string | null;
  sprStBedarf?: string | null;
  verDat?: string | null;
  vkGueltigBis?: string | null;
  zuzahlung?: string | null;
  pRezeptId?: IPRezeptAbr | null;
  pRezeptId?: IPRezeptVer | null;
}

export class PRezeptAbg implements IPRezeptAbg {
  constructor(
    public id?: number,
    public pRezeptId?: string | null,
    public gebFrei?: string | null,
    public gesBrutto?: string | null,
    public hashCode?: string | null,
    public kArt?: string | null,
    public noctu?: string | null,
    public pRezeptTyp?: string | null,
    public rTyp?: string | null,
    public sonstige?: string | null,
    public sprStBedarf?: string | null,
    public verDat?: string | null,
    public vkGueltigBis?: string | null,
    public zuzahlung?: string | null,
    public pRezeptId?: IPRezeptAbr | null,
    public pRezeptId?: IPRezeptVer | null
  ) {}
}

export function getPRezeptAbgIdentifier(pRezeptAbg: IPRezeptAbg): number | undefined {
  return pRezeptAbg.id;
}
