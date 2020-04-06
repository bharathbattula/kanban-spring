
export class UserSession {
  name: string;
  username: string;
  emailId: string;
  token: string;


  constructor(name: string, username: string, emailId: string, token: string) {
    this.name = name;
    this.username = username;
    this.emailId = emailId;
    this.token = token;
  }
}
