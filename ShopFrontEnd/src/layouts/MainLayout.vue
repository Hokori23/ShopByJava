<template>
  <q-layout view="hHh LpR fFf">
    <q-header reveal elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn flat round icon="menu" @click="drawer = !drawer" />

        <q-toolbar-title>Title</q-toolbar-title>

        <q-btn flat round icon="whatshot" />
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above v-model="drawer" side="left" elevated>
      <!-- drawer content -->
    </q-drawer>

    <q-page-container>
      <keep-alive>
        <router-view />
      </keep-alive>
    </q-page-container>
  </q-layout>
</template>


<script>
export default {
  name: "mainLayout",
  data() {
    return {
      drawer: false
    };
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      let login = sessionStorage.getItem("login");
      if (!sessionStorage.getItem("login")) {
        sessionStorage.setItem("login", "0");
        vm.$router.replace("/login");
      }
    });
    // next()
  }
};
</script>
