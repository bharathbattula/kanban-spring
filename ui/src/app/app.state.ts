import {ActionReducerMap} from '@ngrx/store';
import * as boardState from '../app/board/state';

export interface AppState {
  projectDetails:boardState.ProjectDetails;
}

export const initialState: AppState = {
    projectDetails:boardState.initialState
}

export const reducers:ActionReducerMap<AppState> = {
  projectDetails: boardState.reducer
}

export const getBoardProjectDetail = (s:AppState) => s.projectDetails;
