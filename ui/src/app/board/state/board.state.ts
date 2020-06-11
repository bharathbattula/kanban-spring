import {Project} from '../../shared/model/Project';
import {List} from '../../shared/model/List';

export interface ProjectDetails {
  project: Project
  list: List[];
}

export const initialState: ProjectDetails = {} as ProjectDetails;
