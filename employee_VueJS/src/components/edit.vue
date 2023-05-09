
<template>
  <Header />
  <div class="container">
    <div class="row">
      <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
        <h1 class="text-center">Edit Employee</h1>
        <div class="card-body">
          <!-- success message -->
          <div class="alert alert-danger" role="alert" v-bind:style="{ display }">{{ error }}</div>
          
          <form @submit.prevent="update">
            <input type="text" hidden v-model="Employee.id" name="id" class="form-control">
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
              <input type="text" v-model="Employee.email_id" name="email" class="form-control" required
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
              <button type="submit" class="btn btn-primary" style="margin-right:5px ;">Save</button>
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
  name: 'edit',
  components: {
    Header
  },
  data() {
    return {
      Employee: {
        id: '',
        firstName: '',
        lastName: '',
        email_id: '',
        dob: '',
        address: ''
      },
      error: '',
      display: 'none'
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

    update() {
      this.updateData();
    },

    async updateData() {
      var dataa = {
        firstName: this.Employee.firstName,
        lastName: this.Employee.lastName,
        email_id: this.Employee.email_id,
        dob: this.Employee.dob,
        address: this.Employee.address
      }
      if (this.checkEmail() == "") {
        EmployeeService.update(this.Employee.id, dataa)
          .then(() => {
            sessionStorage.setItem("message", true);
            this.$router.push("/home")
          })
          .catch(e => {
            this.error = 'Please check your information'; this.display = "block";
          });
      }
      else {
        this.display = 'block';
        this.error = this.checkEmail();
      }
    },

    getEmployeesById(id) {
      EmployeeService.getEmployeesById(id)
        .then(res => {
          this.Employee = res.data
        })
        .catch(e => console.log(e));
    }
  },
  mounted() {
    if (sessionStorage.getItem("User_email") != null)
      this.getEmployeesById(this.$route.params.id)
    else {
      this.$router.push("/")
      alert("you must login!!")
    }
  }
}
</script>
  
<style scoped></style>
  