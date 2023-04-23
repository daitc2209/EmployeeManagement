
<script>
import { RouterLink, RouterView } from 'vue-router'
import edit from '../components/edit.vue'
export default {
   name:"EditView",
    components: {
        edit
  }
}
</script>

<template>
    <edit/>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
