<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Create New Admin</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">

                <!-- success message -->
                <div class="alert alert-danger" role="alert" v-bind:style="{ display }">{{ error }}</div>

                <h1>Create new User</h1>

                <form @submit.prevent="save()">
                    <div class="mb-3">
                        <label for="email" class="form-label"><b>Email: </b></label>
                        <input type="text" v-model="User.email" class="form-control" id="email" name="email"
                            aria-describedby="" placeholder="eg: newEmail@gmail.com" required autofocus>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label"><b>Password: </b></label>
                        <input type="text" v-model="User.password" class="form-control" id="password" name="password"
                            aria-describedby="" required autofocus minlength="3" maxlength="20">
                    </div>
                    <!-- <div class="mb-3">
                        <label for="role" class="form-label"><b>Role: </b></label>
                        <input type="text" v-model="User.role" class="form-control" id="role" name="role"
                            aria-describedby="" required autofocus
                            placeholder="Please enter your role: ROLE_ADMIN OR ROLE_USER">
                    </div> -->
                    <div class="form-group">
                        <label for="" class="form-label"><b>role: </b></label>
                        <div>
                            <input type="radio" v-model="User.role" name="role" value="ROLE_ADMIN" class="form-check-input">
                            <label class="form-check-label">ROLE_ADMIN</label>
                            <br>
                            <input type="radio" v-model="User.role" name="role" value="ROLE_SUPER_ADMIN" class="form-check-input">
                            <label class="form-check-label">ROLE_SUPER_ADMIN</label>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="name" class="form-label"><b>Name: </b></label>
                        <input type="text" v-model="User.name" class="form-control" id="name" name="name"
                            aria-describedby="" required autofocus>
                    </div>
                    <div class="mb-3">
                        <label for="" class="form-label"><b>Active: </b></label>
                        <div>
                            <input style="margin-right: 5px;" type="radio" v-model="User.active" name="active" value="true"
                                class="form-check-input">
                            <label class="form-check-label">Yes</label>
                            <br>
                            <input style="margin-right: 5px;" type="radio" v-model="User.active" name="active" value="false"
                                class="form-check-input">
                            <label class="form-check-label">No</label>
                        </div>
                        <button type="submit" class="btn btn-primary" style="margin-right: 5px;">Submit</button>
                        <a href="/homeAdmin" class="btn btn-info">Cancel</a>
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
                role: '',
                active: ''

            },
            error: '',
            display: 'none'
        }
    },
    methods: {
        checkEmail() {
            let msg = "";
            if (!Users.checkEmail(this.User.email)) {
                msg += "Email không đúng định dạng";
            }
            return msg;
        },
        save() {
            this.register();
        },
        async register() {
            if (this.checkEmail() == "") {
                console.log(this.User)
                Users.created1(this.User)
                    .then(() => {
                        sessionStorage.setItem("message1", true);
                        this.$router.push({ name: 'homeAdmin' })
                    })
                    .catch((err) => { this.error = 'Invaild username/password'; this.display = "block"; })
            }
            else {
                this.display = 'block';
                this.error = this.checkEmail();
            }

        },
    },

    mounted() {
        if (sessionStorage.getItem("User_email") != null) {
            if (sessionStorage.getItem("role") != "ROLE_SUPER_ADMIN") {
                this.$router.push("/home")
                alert("you are not SUPER ADMIN!!")
            }
        }
        else {
            this.$router.push("/")
            alert("you must login!!")
        }
    }
}
</script>

<style></style>