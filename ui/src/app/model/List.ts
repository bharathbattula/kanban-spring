import {Task} from "./Task";
import {Project} from "./Project";

export interface List {
  id: number;
  name: string;
  project?: Project;
  tasks: Task[];
}
