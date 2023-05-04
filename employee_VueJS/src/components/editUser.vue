
<template>
  <Header/>
  <div class="container">
    <div class="row">
      <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
        <h1 class="text-center">Edit User</h1>
        <div class="card-body">
          <form @submit="update">
            <input type="text" hidden v-model="user.id" name="id" class="form-control">
            <div class="form-group">
              <label>Email: </label>
              <input type="text" v-model="user.email" name="email" class="form-control" required
                placeholder="Enter email...">
            </div>
            <br>
            <div class="form-group">
              <label>Name: </label>
              <input type="text" v-model="user.name" name="name" class="form-control" required
                placeholder="Enter name...">
            </div>
            <br>
            <div class="form-group">
              <label>Role: </label>
              <input type="text" v-model="user.role" name="email" class="form-control" required
                placeholder="Enter email...">
            </div>
            <br>
            <div class="form-group">
              <label for="" class="form-label"><b>Active: </b></label>
              <div>
                <input type="radio" v-model="user.active" name="active" value="true" class="form-check-input">
                <label class="form-check-label">Yes</label>
                <br>
                <input type="radio" v-model="user.active" name="active" value="false" class="form-check-input">
                <label class="form-check-label">No</label>
              </div>
            </div>
            <br>
            <div class="box-footer">
              <button type="submit" class="btn btn-primary" style="margin-right:5px ;">Save</button>
              <a href="/homeAdmin" class="btn btn-info">Cancel</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from './Header.vue'
import Users from '../service/Users'
export default {
  name: 'editUser',
  components: {
    Header
  },
  data() {
    return {
      user: {
        id: '',
        email: '',
        name: '',
        role: '',
        active: ''
      }
    }
  },
  methods: {
    update() {
      this.updateData();
    },
    
    updateData() {
      var dataa = {
        id: this.user.id,
        email: this.user.email,
        name: this.user.name,
        role: this.user.role,
        active: this.user.active
      }
      
      Users.update(this.user.id, dataa)
        .then(() => {
          sessionStorage.setItem("message",true);
          alert("Edit successfully!!! Please press F5");
        })
        .catch(e => {
          alert("Please check your information")
        });
      this.$router.push({ name: 'homeAdmin' })
    },

    getUserById(id) {
      Users.getUserById(id)
        .then(res => {
          this.user = res.data
          console.log(res.data)
        })
        .catch(e => console.log(e));
    }
  },
  mounted() {
    if(sessionStorage.getItem("User_email") != null){
      if(sessionStorage.getItem("role")=="ROLE_ADMIN")
      this.getUserById(this.$route.params.id)
      else
      {
        this.$router.push("/")
        alert("you must login!!")
      }
    }
    else{
        this.$router.push("/")
        alert("you must login!!")
    }
  }
}
</script>
  
<style scoped></style>
  