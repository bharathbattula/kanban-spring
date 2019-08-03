import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RestService} from "../rest.service";
import {Login} from "../model/Login";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username:FormControl;
  passoword:FormControl;

  loginForm:FormGroup;

  constructor(private fb:FormBuilder, private rest:RestService ) { }

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
      const loginRequstObj = new Login(formValue.username, formValue.password);
      this.rest.request(loginRequstObj,'api/auth/signin','POST')
        .then(response => console.log(`response from server ${response}`))
        .catch(error => console.error(`Failed ${error}`));
    } else {
      console.error('Form is not valid');
    }

  }
}
