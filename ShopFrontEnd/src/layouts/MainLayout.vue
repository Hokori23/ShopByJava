<template>
  <q-layout view="hHh LpR fFf">
    <q-header reveal elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn flat round icon="menu" @click="drawer = !drawer" />

        <q-toolbar-title>Title</q-toolbar-title>

        <q-btn flat round icon="whatshot" />
      </q-toolbar>
    </q-header>

    <q-drawer v-model="drawer" side="left" elevated>
      <!-- drawer content -->
      <q-list>
        <!-- 商城主页 -->
        <q-item
          clickable
          v-ripple
          :active="$route.path === '/'"
          @click="$router.push('/')"
          active-class="text-primary shadow-transition shadow-24 inset-shadow hoverable"
        >
          <q-item-section>商城主页</q-item-section>
        </q-item>

        <!-- 用户中心 -->
        <q-item
          clickable
          v-ripple
          :active="$route.path === '/user'"
          @click="$router.push('/user')"
          active-class="text-primary shadow-transition shadow-24 inset-shadow hoverable"
        >
          <q-item-section>用户中心</q-item-section>
        </q-item>

        <!-- 管理员后台 -->
        <q-item
          clickable
          v-ripple
          :active="$route.path === '/admin'"
          @click="$router.push('/admin')"
          active-class="text-primary shadow-transition shadow-24 inset-shadow hoverable"
        >
          <q-item-section>管理员后台</q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <keep-alive>
        <router-view />
      </keep-alive>
      <q-tabs v-model="tab" no-caps class="bg-orange text-white shadow-2">
        <q-tab name="/home" label="Home" @click="nav('/')" />
        <q-tab name="/cart" label="Cart" @click="nav('/cart')" />
        <q-tab name="/log" label="Buy Logs" @click="nav('/log')" />
      </q-tabs>
    </q-page-container>
  </q-layout>
</template>


<script>
import { CLIENT_RENEG_WINDOW } from "tls";
export default {
  name: "mainLayout",
  data() {
    return {
      drawer: false,
      tab: null
    };
  },
  methods: {
    nav(val) {
      this.tab = val;
      if (this.$route.path != val) {
        this.$router.push(val);
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.tab = vm.$route.path;
    });
  }
};
</script>
<style lang="sass">
.page
  height: calc(100vh - 50px - 48px)
  overflow-y: auto
</style>
