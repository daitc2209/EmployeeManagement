<template>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">Login Employee</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
<div class = "container">
    <div class = "row">
        <div class = "col-md-6 col-md-offset-3">

            <h1>Employee Login Page </h1>
            <form @submit.prevent="login">

<!--                 error message-->
    <div class="alert alert-danger" role="alert" v-bind:style="{display}">{{ error }}</div>

    <div class="alert alert-info" role="alert" v-bind:style="{display1}">{{ message }}</div>


                <div class = "form-group">
                    <label for ="email"> Email </label> :
                    <input type="text" class = "form-control" id ="email" name = "email" required
                        v-model="Employee.email_id" placeholder="Enter Email ID" autofocus="autofocus">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>:
                    <input type="password"
                           id="password" name="password" class="form-control" required
                        v-model="Employee.password" placeholder="Enter Password" />
                </div>

                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <input type="submit" name="login-submit" id="login-submit"
                                   class="form-control btn btn-primary" value="Log In" />
                        </div>
                    </div>
                </div>
                
            </form>
            <br>
            <div class="form-group">
                <a href="/">Login with Admin</a>
                <br>
                <span>New user? <a href="/register">Register
								here</a></span>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import EmployeeService from '../service/EmployeeService';
    export default {
        data() {
        return {
            Employee: {
                email_id: '',
                password: '',
            },
            error:'',
            message: '',
            display: 'none',
            display1: 'none',
        }
    },
    methods: {
        async login() {
            console.log(this.Employee)
            EmployeeService.login(this.Employee)
                .then((res) => {
                    console.log(res.data)
                    if(res.data != null){
                        sessionStorage.clear();
                        sessionStorage.setItem("jwtToken", res.data);
                        sessionStorage.setItem("Employee_email", this.Employee.email_id);
                        this.$router.push("/homeEmp")
                    }
                })
                .catch((err) => { this.error = 'Invaild username/password'; this.display="block"; })
        }
    },
    mounted(){
        if(sessionStorage.getItem("message1"))
        {
            this.display1="block";
            this.message="Register successfully!!"
        }
        else{
            this.display1='none';
            this.message='';
        }
    }
}
</script>

<style>

</style>