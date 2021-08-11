import { IMuster16 } from 'app/entities/muster-16/muster-16.model';

export interface IM16Status {
  id?: number;
  m16StatusId?: string | null;
  status?: string | null;
  m16StatusId?: IMuster16 | null;
}

export class M16Status implements IM16Status {
  constructor(
    public id?: number,
    public m16StatusId?: string | null,
    public status?: string | null,
    public m16StatusId?: IMuster16 | null
  ) {}
}

export function getM16StatusIdentifier(m16Status: IM16Status): number | undefined {
  return m16Status.id;
}
