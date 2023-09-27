import { ICategory } from "./category.model";

export interface ITargetInventory {
  targetId?: number;
  targetMonthYear?: string;
  category?: ICategory;
  targetQuantity?: number;
  unit?: string;
}

export const defaultValue: Readonly<ITargetInventory> = {};
