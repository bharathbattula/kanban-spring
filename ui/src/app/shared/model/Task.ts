import {User} from "./User";

export interface Task {

  id?: number;
  title?: string;
  description?: string;
  creator?: User;
  deadLine?: Date;
  participants?: User[];
  edit?: false;
  listId?: number;
}
