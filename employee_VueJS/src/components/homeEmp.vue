<template>
    <h1>Welcome Employee</h1>
    <h6 class="display-6 text-center" style="font-size: 24px">Welcome {{ EmpEmail }}</h6>
    <a class="btn btn-danger" style="margin-left: 10px" @click="logout" href="/loginEmp">Logout</a>
</template>
<script>
export default {
    name: 'homeEmp',
    data() {
        return {
            EmpEmail: sessionStorage.getItem("Employee_email"),
        }

    },
    methods: {
        logout() {
            sessionStorage.removeItem("jwtToken");
            sessionStorage.removeItem("Employee_email");
        }
    }
}
</script>