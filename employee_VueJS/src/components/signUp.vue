<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">Sign Up</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">

                <!-- success message -->
                <!-- <div if="">
        <div class="alert alert-info">You've successfully registered !</div>
      </div> -->

                <h1>Registration</h1>

                <form @submit="save">
                    <div class="form-group">
                        <label class="control-label" for="role"> Name </label>
                        <input id="name" class="form-control" v-model="User.name" autofocus="autofocus" />
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="control-label" for="email"> Email </label>
                        <input id="email" class="form-control" required v-model="User.email" autofocus="autofocus" />
                    </div>
                    <br>
                    <div class="form-group">
                        <label class="control-label" for="password"> Password </label>
                        <input id="password" class="form-control" type="password" v-model="User.password" required
                            autofocus="autofocus" minlength="3" maxlength="20" />
                    </div>
                    <br>
                    <!-- <div class="form-group" hidden>
                        <input id="isActive" class="form-control" type="isActive" value="1" />
                    </div> -->
                    <div class="form-group">
                        <button type="submit" class="btn btn-success" style="margin-right: 4px;">Register</button>
                        <span>Already registered? <a href="/">Login
                                here</a></span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import Users from '../service/Users';
export default {
    data() {
        return {
            User: {
                email: '',
                password: '',
                name: '',
            }
        }
    },
    methods: {
        save() {
            this.register();
        },
        register() {
            console.log(this.User)
            Users.created1(this.User)
                .then(() => {
                    alert("Register successfully!!!");
                })
                .catch((err) => { console.log(err) })

                this.$router.push({name: 'LoginView'})
        }
    },
    created() {
        // this.getEmployees()
    }
}
</script>

<style></style>