
<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">Register</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
                <h1 class="text-center">Register</h1>
                <div class="alert alert-danger" role="alert" v-bind:style="{display}">{{ error }}</div>
                <div class="card-body">
                    <form @submit.prevent="saveData">
                        <div class="form-group">
                            <label>First name: </label>
                            <input type="text" v-model="Employee.firstName" name="firstName" class="form-control" required
                                placeholder="Enter first name...">
                        </div>
                        <br>
                        <div class="form-group">
                            <label>Last name: </label>
                            <input type="text" v-model="Employee.lastName" name="lastName" class="form-control" required
                                placeholder="Enter last name...">
                        </div>
                        <br>
                        <div class="form-group">
                            <label>Email: </label>
                            <input type="text" v-model="Employee.email_id" name="email_id" class="form-control" required
                                placeholder="Enter email...">
                        </div>
                        <br>
                        <div class="form-group">
                            <label>Password: </label>
                            <input type="text" v-model="Employee.password" name="password" class="form-control" required
                                placeholder="Enter password...">
                        </div>
                        <br>
                        <div class="form-group">
                            <label>Date of birth: </label>
                            <input type="text" v-model="Employee.dob" name="dob" class="form-control" required
                                placeholder="Enter Date of birth: YYYY-MM-DD...">
                        </div>
                        <br>
                        <div class="form-group">
                            <label>Address: </label>
                            <input type="text" v-model="Employee.address" name="address" class="form-control" required
                                placeholder="Enter Address...">
                        </div>
                        <br>
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary" style="margin-right: 5px;">Register</button>
                            <a href="/loginEmp" class="btn btn-info">Cancel</a>
                        </div>
                    </form>
                </div>
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
            error:'',
            display: 'none',
        }
    },
    methods: {
        saveData() {
            console.log(this.Employee)
            EmployeeService.create(this.Employee)
                .then(() => {
                    sessionStorage.setItem("message1",true)
                    // this.display="block"; this.error = 'Register successfully !!';
                    this.$router.push("/loginEmp")
                })
                .catch((err) => { this.display="block"; this.error = 'Please check your info';})
        }
    },
    created() {
    }
}
</script>
  