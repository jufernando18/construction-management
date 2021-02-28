import { IMaterial } from 'app/entities/material/material.model';

export interface IBuildType {
  id?: number;
  name?: string;
  duration?: number;
  amountMaterial1?: number;
  amountMaterial2?: number;
  amountMaterial3?: number;
  amountMaterial4?: number;
  amountMaterial5?: number;
  material1?: IMaterial;
  material2?: IMaterial | null;
  material3?: IMaterial | null;
  material4?: IMaterial | null;
  material5?: IMaterial | null;
}

export class BuildType implements IBuildType {
  constructor(
    public id?: number,
    public name?: string,
    public duration?: number,
    public amountMaterial1?: number,
    public amountMaterial2?: number,
    public amountMaterial3?: number,
    public amountMaterial4?: number,
    public amountMaterial5?: number,
    public material1?: IMaterial,
    public material2?: IMaterial | null,
    public material3?: IMaterial | null,
    public material4?: IMaterial | null,
    public material5?: IMaterial | null
  ) {}
}
