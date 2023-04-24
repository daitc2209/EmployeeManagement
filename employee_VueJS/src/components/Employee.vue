<template>
  <Header/>
  <div class="container">
    <div class="d-flex bd-highlight mb-3">
      <div class="me-auto p-2 bd-highlight">
        <h2>Employees List</h2>
      </div>
      <div class="p-2 bd-highlight" style="display: flex;">
        <a href="/create" class="btn btn-primary">Create</a>
      </div>
    </div>
    <div class="d-flex mb-3">
      <h6 class="display-6 text-center" style="font-size: 24px">Welcome {{ UserEmail }}</h6>
      <a class="btn btn-danger" style="margin-left: 10px" @click="logout" href="/">Logout</a>
    </div>
    <!-- <h1 class="text-center">Employees List</h1> -->
    <table class="table">
      <thead>
        <tr>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col">Email</th>
          <th scope="col">Age</th>
          <th scope="col">Date of birth</th>
          <th scope="col">Address</th>
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
          <td>
            <a :href="'/edit/' + emp.id" class="btn btn-secondary" style="margin-left: 5px;">Edit</a>
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
      UserEmail: window.localStorage.getItem("User_email"),
    }
  },
  methods: {
    getEmployees() {
      EmployeeService.getEmployees().then((res => {
        console.log(res)
        this.employees = res.data;
      }))
        .catch((err) => { console.log(err) });
    },

    remove(emp) {
      EmployeeService.remove(emp).then(() => {
        alert("Deleteddd");
        this.getEmployees()
      })
        .catch((err) => { console.log(err) });
    },

    logout() {
      window.localStorage.removeItem("jwtToken");
      window.localStorage.removeItem("User_email");
    }
  },
  created() {
    if(window.localStorage.getItem("User_email") != null)
      this.getEmployees()
      else
      {
        this.$router.push("/")
        alert("you must login!!")
      }

  }
}
</script>
  
<style></style>
  