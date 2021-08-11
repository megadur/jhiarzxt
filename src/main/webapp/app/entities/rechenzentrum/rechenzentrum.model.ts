import { IApotheke } from 'app/entities/apotheke/apotheke.model';

export interface IRechenzentrum {
  id?: number;
  iD?: string | null;
  name?: string | null;
  apothekes?: IApotheke[] | null;
}

export class Rechenzentrum implements IRechenzentrum {
  constructor(public id?: number, public iD?: string | null, public name?: string | null, public apothekes?: IApotheke[] | null) {}
}

export function getRechenzentrumIdentifier(rechenzentrum: IRechenzentrum): number | undefined {
  return rechenzentrum.id;
}
