export interface ITargetInventory {
  id?: number;
  itemName?: string;
  quantity?: number;
}

export const defaultValue: Readonly<ITargetInventory> = {};
