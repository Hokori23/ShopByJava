<template>
  <section class="page">
    <q-list class="list-container non-selectable" :bordered="!$q.screen.lt.md">
      <q-item-label header>用户中心</q-item-label>

      <!-- 昵称 -->
      <q-item clickable v-ripple @click="editNickName">
        <q-item-section avatar>
          <q-icon name="chrome_reader_mode" />
        </q-item-section>
        <q-item-section>
          <q-item-label>昵称</q-item-label>
        </q-item-section>
        <q-item-section class="text-primary text-weight-bold">
          <q-item-label>{{user.name}}</q-item-label>
        </q-item-section>
        <q-item-section side>
          <q-icon name="create" color="grey" />
        </q-item-section>
      </q-item>

      <!-- ID -->
      <q-item class="cursor-not-allowed" :class="$q.dark.mode?'dimmed':'light-dimmed'">
        <q-item-section avatar>
          <q-icon name="account_circle" />
        </q-item-section>
        <q-item-section>
          <q-item-label>账号</q-item-label>
        </q-item-section>
        <q-item-section class="text-primary text-weight-bold" side>
          <q-item-label>{{user.id}}</q-item-label>
        </q-item-section>
      </q-item>

      <!-- 注销 -->
      <q-item clickable v-ripple @click="cancelAccount">
        <q-item-section avatar>
          <q-icon name="delete_forever" />
        </q-item-section>
        <q-item-section>
          <q-item-label>注销</q-item-label>
        </q-item-section>
      </q-item>

      <!-- 退出登录 -->
      <q-item clickable v-ripple @click="logOut">
        <q-item-section avatar>
          <q-icon name="exit_to_app" />
        </q-item-section>
        <q-item-section>
          <q-item-label>登出</q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </section>
</template>
<script>
export default {
  name: "user",
  computed: {
    user() {
      return this.$store.state.Common.user;
    }
  },
  methods: {
    editNickName() {
      this.$q
        .dialog({
          title: "输入框",
          message: "修改信息",
          prompt: {
            model: this.user.name,
            type: "text"
          },
          cancel: true
        })
        .onOk(async name => {
          this.$q
            .dialog({
              title: "输入框",
              message: "新密码",
              prompt: {
                type: "password"
              },
              cancel: true
            })
            .onOk(async password => {
              this.$q
                .dialog({
                  title: "输入框",
                  message: "输入旧密码",
                  prompt: {
                    type: "password"
                  },
                  cancel: true
                })
                .onOk(async old_password => {
                  let res = await this.$request.userEdit(
                    name,
                    password,
                    old_password
                  );
                  if (this.$request.dealErr(res,this)) {
                    this.$q.notify({
                      message: res.data.message,
                      timeout: 250
                    });
                    this.$store.commit("Common/user", res.data.data);
                  }
                });
            });
        });
    },
    cancelAccount() {
      this.$q
        .dialog({
          title: "注销",
          message: "输入密码验证进行账号注销",
          prompt: {
            type: "password"
          },
          cancel: true
        })
        .onOk(async data => {
          let res = await this.$request.userCancel(data);
          if (this.$request.dealErr(res,this)) {
            this.$q.notify({
              message: res.data.message,
              timeout: 250
            });
            await this.$request.userLogOut();
            this.$store.commit("Common/user", "");
            this.$router.push("/");
          }
        });
    },
    logOut() {
      this.$q
        .dialog({
          title: "提示",
          message: "确定要退出吗",
          cancel: true
        })
        .onOk(async () => {
          let res = await this.$request.userLogOut();
          if (this.$request.dealErr(res,this)) {
            this.$q.notify({
              message: res.data.message,
              timeout: 250
            });
            this.$store.commit("Common/user", "");
            this.$router.push("/");
          }
        });
    }
  }
};
</script>
<style lang="sass" scoped>
.page
  width: 100%
  position: relative
  .list-container
    position: relative
    max-width: 450px
    margin: 0 auto
    min-height: calc(100vh - 50px)
    min-width: 500px
</style>
