import * as programmable from './board.actions';
import {SET_WORKING_PROJECT, SET_WORKING_PROJECT_LIST} from './board.actions';
import {ProjectDetails} from './board.state';

export function reducer(state: ProjectDetails, action: programmable.BoardActions): any {

  switch (action.type) {
    case SET_WORKING_PROJECT: {
      return {
        project: action.payload,
        list: []
      };
    }
    case SET_WORKING_PROJECT_LIST: {
      return {
        ...state,
        list: action.payload
      }
    }
  }

}
