import * as dayjs from 'dayjs';
import { IERezept } from 'app/entities/e-rezept/e-rezept.model';

export interface IEAenderung {
  id?: number;
  schluessel?: number | null;
  dokuRezept?: string | null;
  dokuArzt?: string | null;
  datum?: dayjs.Dayjs | null;
  eRezept?: IERezept | null;
}

export class EAenderung implements IEAenderung {
  constructor(
    public id?: number,
    public schluessel?: number | null,
    public dokuRezept?: string | null,
    public dokuArzt?: string | null,
    public datum?: dayjs.Dayjs | null,
    public eRezept?: IERezept | null
  ) {}
}

export function getEAenderungIdentifier(eAenderung: IEAenderung): number | undefined {
  return eAenderung.id;
}
