import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RestService} from "../rest.service";
import {Login} from "../model/Login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username:FormControl;
  passoword:FormControl;

  loginForm:FormGroup;

  constructor(private fb: FormBuilder, private rest: RestService, private router: Router) {
  }

  ngOnInit() {
    this.username = new FormControl("", [Validators.required]);
    this.passoword = new FormControl("", [Validators.required]);

    this.loginForm = this.fb.group( {
      username:['', Validators.required],
      password:['', Validators.required]
    })
    

  }

  login(formValue) {
    if(formValue) {
      console.log(formValue);
      const loginRequestObj = new Login(formValue.username, formValue.password);
      this.rest.request(loginRequestObj, 'auth/signin', 'POST')
        .then(response => {
          this.rest.setSession(response);
          this.router.navigateByUrl('app');
          }
        )
        .catch(error => console.error(`Failed ${error}`));
    } else {
      console.error('Form is not valid');
    }

  }
}
