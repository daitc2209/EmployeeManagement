<template>
  <Header />
  <div class="container">
    <div class="d-flex bd-highlight mb-3">
      <div class="me-auto p-2 bd-highlight">
        <h2>Employees List</h2>
      </div>
    </div>
    <div class="d-flex mb-3">
      <h6 class="display-6 text-center" style="font-size: 24px">Welcome {{ UserEmail }}</h6>
      <a class="btn btn-danger" style="margin-left: 10px" @click="logout" href="/">Logout</a>
    </div>
    <h6 class="display-6 " style="font-size: 24px">Role: {{ role }}</h6>
    <!-- <h1 class="text-center">Employees List</h1> -->
    <div v-if="role === 'ROLE_SUPER_ADMIN'"><a href="/homeAdmin" class="btn btn-primary"
        style="font-size: 24px; margin-bottom: 8px;"> <b>List User</b></a></div>

    <input type="text" v-model="searchText" class="form-control" placeholder="Search...">
    <br>

    <!-- Messageeee -->
    <div class="alert alert-info" role="alert" v-bind:style="{ display }">{{ message }}</div>

    <table class="table">
      <thead>
        <tr>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col">Email</th>
          <th scope="col">Age</th>
          <th scope="col">Date of birth</th>
          <th scope="col">Address</th>
          <th scope="col">Active</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-for="emp in employees" v-bind:key="emp.id">
          <td>{{ emp.firstName }}</td>
          <td>{{ emp.lastName }}</td>
          <td>{{ emp.email_id }}</td>
          <td>{{ emp.age }}</td>
          <td>{{ emp.dob }}</td>
          <td>{{ emp.address }}</td>
          <td>{{ emp.active }}</td>
          <td>
            <a :href="'/edit/' + emp.id" class="btn btn-secondary" style="margin-right: 5px;">Edit</a>
            <a @click="remove(emp.id)" class="btn btn-danger">Delete</a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
  
<script>
import Header from './Header.vue'
import EmployeeService from '../service/EmployeeService'
export default {
  name: 'Employees',
  components: {
    Header
  },
  data() {
    return {
      employees: [],
      UserEmail: sessionStorage.getItem("User_email"),
      display: 'none',
      message: '',
      role: sessionStorage.getItem("role"),
      searchText: ''
    }
  },
  methods: {
    getEmployees() {
      this.$router.push("/home")
      EmployeeService.getEmployees().then((res => {
        console.log(res)
        this.employees = res.data.data;
      }))
        .catch((err) => { console.log(err) });
    },

    remove(emp) {

      if (confirm("Delete Confirmation") == true) {
        EmployeeService.remove(emp).then(() => {
          sessionStorage.setItem("message3", true);
          location.reload();
          this.getEmployees()
        })
          .catch((err) => { console.log(err) });
      }
      else {
        this.getEmployees()
      }
    },

    logout() {
      sessionStorage.removeItem("jwtToken");
      sessionStorage.removeItem("User_email");
      sessionStorage.removeItem("role");
    },

    searchEmployees() {
      EmployeeService.searchEmployees({
        params: {
          keyword: this.searchText
        }
      })
        .then(response => {
          this.employees = response.data.data;
        });
    }
  },
  watch: {
    searchText() {
      this.searchEmployees();
    }
  },


  mounted() {

    if (sessionStorage.getItem("User_email") != null) {
      if (sessionStorage.getItem("message")) {
        this.display = 'block',
          this.message = 'Edit successfully !!!'
        sessionStorage.removeItem("message")
      }
      if (sessionStorage.getItem("message1")) {
        this.display = 'block',
          this.message = 'Register successfully !!!'
        sessionStorage.removeItem("message1")
      }
      if (sessionStorage.getItem("message2")) {
        this.display = 'block',
          this.message = 'Login successfully !!!'
        sessionStorage.removeItem("message2")
      }
      if (sessionStorage.getItem("message3")) {
        this.display = 'block',
          this.message = 'Delete successfully !!!'
        sessionStorage.removeItem("message3")
      }
      this.getEmployees()

    }
    else {
      this.$router.push("/")
      alert("you must login!!")
    }
  }

}
</script>
  
<style></style>
  