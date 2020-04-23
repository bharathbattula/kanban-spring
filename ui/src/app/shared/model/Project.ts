import {List} from "./List";
import {User} from "./User";

export class Project {
  id?: number;
  name: string;
  description?: string;
  createdAt?: Date;
  updatedAt?: Date;
  creator?: User;
  list?: List[];
  users?: User[];
}
