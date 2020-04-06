import {User} from "./User";

export interface Task {

  id?: number;
  title: string;
  description: string;
  user?: User;
  deadLine?: Date;
  participants?: string[];
}
