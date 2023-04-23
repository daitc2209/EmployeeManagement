
<template>
    <Header/>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
                <h1 class="text-center">Create new Employee</h1>
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
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <a href="/home" class="btn btn-info">Cancel</a>
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
                dob: '',
                address: ''
            }
        }
    },
    methods: {
        saveData() {
            console.log(this.Employee)
            EmployeeService.create(this.Employee)
                .then(() => {
                    alert("saveddd");
                    this.$router.push({name: 'home'})
                })
                .catch((err) => { alert("Can't add new Employee") })
            
        }
    },
    created() {
    if(window.localStorage.getItem("User_email") == null)
      {
        this.$router.push("/")
        alert("you must login!!")
      }
    }
}
</script>
  