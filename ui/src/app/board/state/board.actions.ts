import {Action} from '@ngrx/store';

export const SET_WORKING_PROJECT = 'SetWorkingProject';
export const SET_WORKING_PROJECT_LIST = 'SetWorkingProjectList';
export const FETCH_PROJECT_LIST = 'FetchProjectList';

export class SetWorkingProject implements Action {
  readonly type: string = SET_WORKING_PROJECT;

  constructor(public payload: any) {
  }
}

export class SetWorkingProjectList implements Action {
  type: string = SET_WORKING_PROJECT_LIST;

  constructor(public payload: any) {
  }
}

export class FetchProjectList implements Action {
  readonly type: string = FETCH_PROJECT_LIST;

  constructor(public payload: any) {
  }
}

export type BoardActions = SetWorkingProject | SetWorkingProjectList | FetchProjectList;
