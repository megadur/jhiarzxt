import { IMuster16Abg } from 'app/entities/muster-16-abg/muster-16-abg.model';
import { IMuster16 } from 'app/entities/muster-16/muster-16.model';

export interface IMuster16Ver {
  id?: number;
  aUnfall?: string | null;
  abDatum?: string | null;
  vrsNr?: string | null;
  vrtrgsArztNr?: string | null;
  sprStBedarf?: string | null;
  unfall?: string | null;
  unfallTag?: string | null;
  vGeb?: string | null;
  vStat?: string | null;
  verDat?: string | null;
  kName?: string | null;
  kkIk?: string | null;
  laNr?: string | null;
  noctu?: string | null;
  hilf?: string | null;
  impf?: string | null;
  kArt?: string | null;
  rTyp?: string | null;
  rezeptTyp?: string | null;
  bgrPfl?: string | null;
  bvg?: string | null;
  eigBet?: string | null;
  gebFrei?: string | null;
  sonstige?: string | null;
  vkGueltigBis?: string | null;
  mRezeptId?: IMuster16Abg | null;
  mRezeptId?: IMuster16 | null;
}

export class Muster16Ver implements IMuster16Ver {
  constructor(
    public id?: number,
    public aUnfall?: string | null,
    public abDatum?: string | null,
    public vrsNr?: string | null,
    public vrtrgsArztNr?: string | null,
    public sprStBedarf?: string | null,
    public unfall?: string | null,
    public unfallTag?: string | null,
    public vGeb?: string | null,
    public vStat?: string | null,
    public verDat?: string | null,
    public kName?: string | null,
    public kkIk?: string | null,
    public laNr?: string | null,
    public noctu?: string | null,
    public hilf?: string | null,
    public impf?: string | null,
    public kArt?: string | null,
    public rTyp?: string | null,
    public rezeptTyp?: string | null,
    public bgrPfl?: string | null,
    public bvg?: string | null,
    public eigBet?: string | null,
    public gebFrei?: string | null,
    public sonstige?: string | null,
    public vkGueltigBis?: string | null,
    public mRezeptId?: IMuster16Abg | null,
    public mRezeptId?: IMuster16 | null
  ) {}
}

export function getMuster16VerIdentifier(muster16Ver: IMuster16Ver): number | undefined {
  return muster16Ver.id;
}
