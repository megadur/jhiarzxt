import { IM16Status } from 'app/entities/m-16-status/m-16-status.model';
import { IMuster16Ver } from 'app/entities/muster-16-ver/muster-16-ver.model';
import { IMArtikel } from 'app/entities/m-artikel/m-artikel.model';
import { ILieferung } from 'app/entities/lieferung/lieferung.model';

export interface IMuster16 {
  id?: number;
  mRezeptId?: string | null;
  lieferungId?: string | null;
  m16Status?: string | null;
  mMuster16Id?: string | null;
  apoIkSend?: string | null;
  apoIkSnd?: string | null;
  m16Status?: IM16Status | null;
  mRezeptId?: IMuster16Ver | null;
  mArtikels?: IMArtikel[] | null;
  lieferungId?: ILieferung | null;
}

export class Muster16 implements IMuster16 {
  constructor(
    public id?: number,
    public mRezeptId?: string | null,
    public lieferungId?: string | null,
    public m16Status?: string | null,
    public mMuster16Id?: string | null,
    public apoIkSend?: string | null,
    public apoIkSnd?: string | null,
    public m16Status?: IM16Status | null,
    public mRezeptId?: IMuster16Ver | null,
    public mArtikels?: IMArtikel[] | null,
    public lieferungId?: ILieferung | null
  ) {}
}

export function getMuster16Identifier(muster16: IMuster16): number | undefined {
  return muster16.id;
}
