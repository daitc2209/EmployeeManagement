
<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Register</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-offset-3">
                <h1 class="text-center">Register</h1>
                <div class="alert alert-danger" role="alert" v-bind:style="{ display }">{{ error }}</div>

                <form @submit.prevent="saveData">
                    <div class="form-group">
                        <label>First name: </label>
                        <input type="text" v-model="Employee.firstName" name="firstName" class="form-control" required
                            placeholder="Enter first name..." autofocus>
                    </div>
                    <br>
                    <div class="form-group">
                        <label>Last name: </label>
                        <input type="text" v-model="Employee.lastName" name="lastName" class="form-control" required
                            placeholder="Enter last name..." autofocus>
                    </div>
                    <br>
                    <div class="form-group">
                        <label>Email: </label>
                        <input type="text" v-model="Employee.email_id" name="email_id" class="form-control" required
                            placeholder="eg: newEmail@gmail.com..." autofocus>
                    </div>
                    <br>
                    <div class="form-group">
                        <label>Password: </label>
                        <input type="text" v-model="Employee.password" name="password" class="form-control" required
                            placeholder="Enter password..." autofocus minlength="3" maxlength="20">
                    </div>
                    <br>
                    <div class="form-group">
                        <label>Date of birth: </label>
                        <input type="text" v-model="Employee.dob" name="dob" class="form-control" required
                            placeholder="Enter Date of birth: YYYY-MM-DD..." autofocus>
                    </div>
                    <br>
                    <div class="form-group">
                        <label>Address: </label>
                        <input type="text" v-model="Employee.address" name="address" class="form-control" required
                            placeholder="Enter Address..." autofocus>
                    </div>
                    <br>
                    <div class="box-footer">
                        <button type="submit" class="form-control btn btn-primary" style="margin-right: 5px;">Register</button>
                        <a href="/loginEmp" class="btn btn-info" style="float: right; margin-top: 6px;">Cancel</a>
                    </div>
                </form>
                
            </div>
        </div>
    </div>
</template>

<script>
import Header from './Header.vue'
import EmployeeService from '../service/EmployeeService'
export default {
    name: 'create',
    components: {
        Header
    },
    data() {
        return {
            Employee: {
                firstName: '',
                lastName: '',
                email_id: '',
                password: '',
                dob: '',
                address: ''
            },
            error: '',
            display: 'none',
        }
    },
    methods: {
        checkEmail() {
            let msg = "";
            if (!EmployeeService.checkEmail(this.Employee.email_id)) {
                msg += "Email không đúng định dạng";
            }
            return msg;
        },
        saveData() {
            if (this.checkEmail() == "") {
                console.log(this.Employee)
                EmployeeService.create(this.Employee)
                    .then(() => {
                        sessionStorage.setItem("message1", true)
                        this.$router.push("/loginEmp")
                    })
                    .catch((err) => { this.display = "block"; this.error = 'Please check your info'; })
            }
            else {
                this.display = 'block';
                this.error = this.checkEmail();
            }
        }

    },
}
</script>
  