import { IPRezept } from 'app/entities/p-rezept/p-rezept.model';

export interface IPStatusInfo {
  id?: number;
  pStatusInfoId?: string | null;
  pRezeptId?: string | null;
  apoIk?: string | null;
  fCode?: string | null;
  fHauptFehler?: string | null;
  fKommentar?: string | null;
  fKurzText?: string | null;
  fLangText?: string | null;
  fStatus?: string | null;
  fTCode?: string | null;
  fWert?: string | null;
  faktor?: string | null;
  fristEnde?: string | null;
  gesBrutto?: string | null;
  posNr?: string | null;
  taxe?: string | null;
  zeitpunkt?: string | null;
  zuzahlung?: string | null;
  pStatusInfoId?: IPRezept | null;
}

export class PStatusInfo implements IPStatusInfo {
  constructor(
    public id?: number,
    public pStatusInfoId?: string | null,
    public pRezeptId?: string | null,
    public apoIk?: string | null,
    public fCode?: string | null,
    public fHauptFehler?: string | null,
    public fKommentar?: string | null,
    public fKurzText?: string | null,
    public fLangText?: string | null,
    public fStatus?: string | null,
    public fTCode?: string | null,
    public fWert?: string | null,
    public faktor?: string | null,
    public fristEnde?: string | null,
    public gesBrutto?: string | null,
    public posNr?: string | null,
    public taxe?: string | null,
    public zeitpunkt?: string | null,
    public zuzahlung?: string | null,
    public pStatusInfoId?: IPRezept | null
  ) {}
}

export function getPStatusInfoIdentifier(pStatusInfo: IPStatusInfo): number | undefined {
  return pStatusInfo.id;
}
