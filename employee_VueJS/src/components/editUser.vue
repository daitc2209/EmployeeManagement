
<template>
  <Header />
  <div class="container">
    <div class="row">
      <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center card">
        <h1 class="text-center">Edit User</h1>
        <div class="card-body">
          <!-- success message -->
          <div class="alert alert-danger" role="alert" v-bind:style="{ display }">{{ error }}</div>

          <form @submit.prevent="update">
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
            <!-- <div class="form-group">
              <label>Role: </label>
              <input type="text" v-model="user.role" name="email" class="form-control" required
                placeholder="Enter email...">
            </div> -->
            <div class="form-group">
              <label for="" class="form-label"><b>role: </b></label>
              <div>
                <input type="radio" v-model="user.role" name="role" value="ROLE_ADMIN" class="form-check-input">
                <label class="form-check-label">ROLE_ADMIN</label>
                <br>
                <input type="radio" v-model="user.role" name="role" value="ROLE_SUPER_ADMIN" class="form-check-input">
                <label class="form-check-label">ROLE_SUPER_ADMIN</label>
              </div>
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
      },
      error: '',
      display: 'none'
    }
  },
  methods: {

    checkEmail() {
      let msg = "";
      if (!Users.checkEmail(this.user.email)) {
        msg += "Email không đúng định dạng";
      }
      return msg;
    },

    update() {
      this.updateData();
    },

    async updateData() {
      var dataa = {
        id: this.user.id,
        email: this.user.email,
        name: this.user.name,
        role: this.user.role,
        active: this.user.active
      }

      if (this.checkEmail() == "") {
        Users.update(this.user.id, dataa)
          .then(() => {
            sessionStorage.setItem("message", true);
            this.$router.push({ name: 'homeAdmin' })

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

    async getUserById(id) {
      Users.getUserById(id)
        .then(res => {
          this.user = res.data.data
          console.log(res.data.data)
        })
        .catch(e => console.log(e));
    }
  },
  mounted() {
    if (sessionStorage.getItem("User_email") != null) {
      if (sessionStorage.getItem("role") == "ROLE_SUPER_ADMIN")
        this.getUserById(this.$route.params.id)
      else {
        this.$router.push("/")
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
  
<style scoped></style>
  