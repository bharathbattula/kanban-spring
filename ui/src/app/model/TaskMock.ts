import {Task} from "./Task";
import {User} from "./User";

export const users:User[] = [
  {id:0, email:'aba@gmail.com', firstName: 'bharat', lastName: 'battula', username:'bb'},
  {id:1, email:'abc@gmail.com', firstName: 'abc', lastName: 'yz', username:'jj'}
]

export const myTasks1: Task[] = [
  new Task(1, "Fix Authentication",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
    users[0],
    ["Auth", "Dev"]),
  new Task(2, "Convert project into dark theme",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    users[1],
    ["Design", "Frontend"]),
  new Task(3, "Setup kafka streaming service for the project",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    users[0],
    ["Server", "kafka", "backend"]
  )
];

export const myTasks2: Task[] = [
  new Task(1, "Setup kafka streaming service for the project",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
    users[0],
    ["Server", "kafka", "backend"]
  ),
  new Task(2, "Convert project into dark theme",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    users[1],
    ["Design", "Frontend"]),
  new Task(3, "Fix Authentication",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
    users[0],
    ["Auth", "Dev"]),
];
