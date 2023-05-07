<template>
  <Header/>
  <div class="container">
    <div class="d-flex bd-highlight mb-3">
      <div class="me-auto p-2 bd-highlight">
        <h2>User List</h2>
      </div>
      <div class="p-2 bd-highlight" style="display: flex;">
        <a href="/create" class="btn btn-primary">Create</a>
      </div>
    </div>
    <div class="d-flex mb-3">
      <h6 class="display-6 text-center" style="font-size: 24px">Welcome {{ UserEmail }}</h6>
      <a class="btn btn-danger" style="margin-left: 10px" @click="logout" href="/">Logout</a>
    </div>
    <h6 class="display-6 " style="font-size: 24px">Role: {{ role }}</h6>

    <input type="text" v-model="searchText" class="form-control" placeholder="Search...">
      <br>

    <div class="alert alert-info" role="alert" v-bind:style="{display}">{{ message }}</div>

    <table class="table">
      <thead>
        <tr>
          <th scope="col">Email</th>
          <th scope="col">Name</th>
          <th scope="col">Role</th>
          <th scope="col">Active</th>
        </tr>
      </thead>
      <tbody class="table-group-divider">
        <tr v-for="u in user" v-bind:key="u.id">
          <td>{{ u.email }}</td>
          <td>{{ u.name }}</td>
          <td>{{ u.role }}</td>
          <td>{{ u.active }}</td>
          <td>
            <a :href="'/editAdmin/' + u.id" class="btn btn-secondary" style="margin-right: 5px;">Edit</a>
            <a @click="remove(u.id)" class="btn btn-danger">Delete</a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
  
<script>
import Header from './Header.vue'
import Users from '../service/Users'
export default {
  name: 'RenderUser',
  components: {
    Header
  },
  data() {
    return {
      user: [],
      UserEmail: sessionStorage.getItem("User_email"),
      display:'none',
      message:'',
      role: sessionStorage.getItem("role"),
      searchText:''
    }
  },
  methods: {
    getUsers() {
      // this.$router.push("/home")
      Users.getUsers().then((res => {
        console.log(res)
        this.user = res.data;
      }))
        .catch((err) => { console.log(err) });
    },

    remove(user) {
      Users.remove(user).then(() => {
        sessionStorage.setItem("message3",true);
        location.reload();
        this.getUsers()
      })
        .catch((err) => { console.log(err) });
    },

    logout() {
      sessionStorage.removeItem("jwtToken");
      sessionStorage.removeItem("User_email");
      sessionStorage.removeItem("role");
    },

    searchUser() {
      Users.searchUser({params:{
          keyword: this.searchText
        }})
      .then(response => {
        this.user = response.data;
      });
    }
  },
  watch: {
    searchText() {
      this.searchUser();
    }
  },
  mounted() {
    if(sessionStorage.getItem("User_email") != null)
    {
      if(sessionStorage.getItem("role")=="ROLE_ADMIN")
        this.getUsers()
      else
      {
        this.$router.push("/home")
        alert("you are not Admin!!")
      }
    }
    else{
        this.$router.push("/")
        alert("you must login!!")
    }

      if(sessionStorage.getItem("message"))
      {
        this.display = 'block',
        this.message = 'Edit successfully !!!'
        sessionStorage.removeItem("message")
      }
      if(sessionStorage.getItem("message1"))
      {
        this.display = 'block',
        this.message = 'Create successfully !!!'
        sessionStorage.removeItem("message1")
      }
      if(sessionStorage.getItem("message2"))
      {
        this.display = 'block',
        this.message = 'Login successfully !!'
        sessionStorage.removeItem("message2")
      }
      if(sessionStorage.getItem("message3"))
      {
        this.display = 'block',
        this.message = 'Delete successfully !!!'
        sessionStorage.removeItem("message3")
      }
  }

}
</script>
  
<style></style>
  