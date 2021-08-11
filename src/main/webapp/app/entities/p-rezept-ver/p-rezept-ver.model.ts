import { IPRezeptAbg } from 'app/entities/p-rezept-abg/p-rezept-abg.model';
import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';

export interface IPRezeptVer {
  id?: number;
  pRezeptId9?: string | null;
  apoIk?: string | null;
  apoIkSend?: string | null;
  arbPlatz?: string | null;
  avsId?: string | null;
  bediener?: string | null;
  bvg?: string | null;
  eStatus?: string | null;
  erstZeitpunkt?: string | null;
  kName?: string | null;
  kkIk?: string | null;
  laNr?: string | null;
  vrsNr?: string | null;
  vrtrgsArztNr?: string | null;
  vGeb?: string | null;
  vStat?: string | null;
  pRezeptId?: IPRezeptAbg | null;
  pRezeptId?: IPRezept | null;
}

export class PRezeptVer implements IPRezeptVer {
  constructor(
    public id?: number,
    public pRezeptId9?: string | null,
    public apoIk?: string | null,
    public apoIkSend?: string | null,
    public arbPlatz?: string | null,
    public avsId?: string | null,
    public bediener?: string | null,
    public bvg?: string | null,
    public eStatus?: string | null,
    public erstZeitpunkt?: string | null,
    public kName?: string | null,
    public kkIk?: string | null,
    public laNr?: string | null,
    public vrsNr?: string | null,
    public vrtrgsArztNr?: string | null,
    public vGeb?: string | null,
    public vStat?: string | null,
    public pRezeptId?: IPRezeptAbg | null,
    public pRezeptId?: IPRezept | null
  ) {}
}

export function getPRezeptVerIdentifier(pRezeptVer: IPRezeptVer): number | undefined {
  return pRezeptVer.id;
}
