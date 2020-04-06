import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {RestService} from "../shared/services/rest.service";
import {Login} from "../shared/model/Login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  loginForm: FormGroup;
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder, private rest: RestService, private router: Router) {
    if (this.rest.currentUserValue) {
      this.router.navigateByUrl('app');
    }
  }

  ngOnInit() {

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.registrationForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.min(8)]]
    });

  }

  login(formValue) {
    if(formValue) {

      const loginRequestObj = new Login(formValue.username, formValue.password);

      this.rest.request(loginRequestObj, 'auth/signin', 'POST')
        .then(response => {
          this.rest.setSession(response);
          this.router.navigateByUrl('app');
        })
        .catch(error => console.error(`Failed ${error}`));
    } else {
      console.error('Form is not valid');
    }

  }

  register(value: any) {
    console.log(value);

    if (value) {
      this.rest.request(value, 'auth/signup', 'POST')
        .then(response => {
          console.log(response);
        })
        .catch(error => console.error(error));
    }

  }
}
