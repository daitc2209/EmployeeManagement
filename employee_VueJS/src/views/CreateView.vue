<script>
import { RouterLink, RouterView } from 'vue-router'
import create from '../components/create.vue'
export default {
   name:"CreateView",
    components: {
    create
  }
}
</script>

<template>
    <create/>
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
