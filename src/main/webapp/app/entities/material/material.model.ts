export interface IMaterial {
  id?: number;
  name?: string;
  acronym?: string;
  amountAvailable?: number;
}

export class Material implements IMaterial {
  constructor(public id?: number, public name?: string, public acronym?: string, public amountAvailable?: number) {}
}
