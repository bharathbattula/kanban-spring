import {Injectable} from '@angular/core';
import {Actions, ofType} from '@ngrx/effects';
import {RestService} from '../../shared/services/rest.service';
import {FETCH_PROJECT_LIST, FetchProjectList, SetWorkingProjectList} from './board.actions';
import {switchMap} from 'rxjs/operators';


@Injectable()
class BoardEffects {

  constructor(private actions$: Actions, private restService: RestService) {
  }

  public fetchProjectList$ = this.actions$.pipe(
    ofType(FETCH_PROJECT_LIST),
    switchMap((action: FetchProjectList) =>
        this.restService.request(null, `/project/${action.payload.projectId}/list`, 'GET')
          .then(value1 => new SetWorkingProjectList(value1))
    ),
  )
}
