import {List} from "./List";

export class Project {
  id?: number;
  name: string;
  description?: string;
  createdAt?: Date;
  updatedAt?: Date;
  list?: List[];
}
