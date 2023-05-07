<template>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">Login</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
<div class = "container">
    <div class = "row">
        <div class = "col-md-6 col-md-offset-3">

            <h1> Login Admin Page </h1>
            <form @submit.prevent="login">

<!--                 error message-->
                    <div class="alert alert-danger" role="alert" v-bind:style="{display}">{{ error }}</div>



                <div class = "form-group">
                    <label for ="email"> Email </label> :
                    <input type="text" class = "form-control" id ="email" name = "email" required
                        v-model="User.email" placeholder="Enter Email ID" autofocus="autofocus">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>:
                    <input type="password"
                           id="password" name="password" class="form-control" required
                        v-model="User.password" placeholder="Enter Password" />
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
						<span>You are employee? <a href="/loginEmp">Login with Empolyee</a></span>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import axios from 'axios';
import Users from '../service/Users';
    export default {
        data() {
        return {
            User: {
                email: '',
                password: '',
                role: ''
            },
            error:'',
            display: 'none',
        }
    },
    methods: {
        login() {
            console.log(this.User)
            Users.login(this.User)
                .then((res) => {
                    console.log(res.data)
                    if(res.data != null){
                        sessionStorage.clear();
                        sessionStorage.setItem("jwtToken", res.data.token);
                        sessionStorage.setItem("role", res.data.role);
                        sessionStorage.setItem("User_email", this.User.email);
                        sessionStorage.setItem("message2",true)
                        axios.defaults.headers.Authorization = `Bearer ${res.data.token}`;
                        this.$router.push("/home")
                    }
                })
                .catch((err) => { this.display="block"; this.error = 'Invaild username/password'; })

                
        }
    },
    created(){
       
    }
}
</script>

<style>

</style>