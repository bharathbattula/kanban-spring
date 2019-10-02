import {User} from "./User";

export class Task {

  constructor(public id: number,
              public title: string,
              public description: string,
              public user: User,
              public tags: string[]) {
  }
}
