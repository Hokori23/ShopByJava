<template>
  <q-page>
    <q-stepper
      v-model="step"
      ref="stepper"
      color="primary"
      animated
      header-nav
      keep-alive
      swipeable
      :contracted="$q.screen.lt.sm"
      class="stepper"
    >
      <!-- Step Title -->
      <q-step
        :name="1"
        title="Select campaign settings"
        icon="create"
        :done="step > 1"
        class="q-px-lg q-py-md"
      >
        <!-- 账号或邮箱 -->
        <q-input
          bottom-slots
          v-model="info.account"
          :label="$t('login.accountOrEmail')"
          :error-message="text.account"
          ref="accountInput"
          :error="!valid.account"
          @focus="clear('account')"
          @clear="clear('account')"
        >
          <template v-slot:prepend>
            <q-icon name="account_circle" />
          </template>
          <template v-slot:append>
            <q-icon name="close" @click="info.account = ''" class="cursor-pointer" />
          </template>
        </q-input>
        <!-- 昵称 -->
        <q-input
          bottom-slots
          v-model="info.name"
          :label="$t('login.nickName')"
          :error-message="text.name"
          ref="nameInput"
          :error="!valid.name"
          @focus="clear('name')"
          @clear="clear('name')"
        >
          <template v-slot:prepend>
            <q-icon name="chrome_reader_mode" />
          </template>
          <template v-slot:append>
            <q-icon name="close" @click="info.name = ''" class="cursor-pointer" />
          </template>
        </q-input>
        <!-- 密码 -->
        <q-input
          bottom-slots
          v-model="info.password"
          :label="$t('login.password')"
          :error-message="text.password"
          ref="passwordInput"
          :error="!valid.password"
          @focus="clear('password')"
          @clear="clear('password')"
        >
          <template v-slot:prepend>
            <q-icon name="lock" />
          </template>
          <template v-slot:append>
            <q-icon name="close" @click="info.password = ''" class="cursor-pointer" />
          </template>
        </q-input>
      </q-step>

      <q-step
        :name="2"
        title="Create an ad group"
        caption="Optional"
        icon="create_new_folder"
        :done="step > 2"
      >
        <div class="absolute-center">{{$t('login.register')}}</div>
      </q-step>

      <template v-slot:navigation>
        <q-stepper-navigation class="navigation">
          <q-btn
            @click="next()"
            color="primary"
            :label="step === 2 ? $t('common.finish') : $t('common.continue')"
          />
          <q-btn
            v-if="step > 1"
            flat
            color="primary"
            @click="$refs.stepper.previous()"
            :label="$t('common.back')"
            class="q-ml-sm"
          />
        </q-stepper-navigation>
      </template>
    </q-stepper>
  </q-page>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      step: 1,
      info: {
        account: "",
        name: "",
        password: ""
      },
      valid: {
        account: true,
        name: true,
        password: true
      },
      text: {
        account: this.$t("login.accountErr"),
        name: this.$t("login.nickNameErr"),
        password: this.$t("login.passwordErr")
      }
    };
  },
  methods: {
    clear(value) {
      this.valid[value] = true;
    },
    next() {
      if (this.step === 1) {
        let flag = true;
        if (!this.info.account) {
          this.text.account = this.$t("login.accountErr");
          this.valid.account = false;
          flag = false;
        }
        if (!this.info.password) {
          this.text.password = this.$t("login.passwordErr");
          this.valid.password = false;
          flag = false;
        }
        if (!this.info.name) {
          this.text.name = this.$t("login.nickNameErr");
          this.valid.name = false;
          flag = false;
        }
        if (flag) {
          this.$refs.stepper.next();
        }
      } else if (this.step === 2) {
      //Register.vue
      this.$axios
        .post("/user", {
          id: this.info.account,
          password: this.info.password,
          name: this.info.name
        })
        .then(res => {
          console.log(res);
          if (res.data.errcode) {
            this.$q.dialog({
              message: res.data.message
            });
          } else {
            sessionStorage.setItem("login", "1");
            sessionStorage.setItem("name", res.data.data.name);
            sessionStorage.setItem("id", res.data.data.id);
            this.$router.push("/");
          }
        })
          .catch(e => {
            this.$q.dialog({
              message: e.message
            });
          });
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$emit("header", true);
      vm.$store.commit("LoginLayout/titleText", vm.$t("login.register"));
    });
  }
};
</script>

<style lang="sass" scoped>
.stepper
  min-height: calc(100vh - 50px)
  position: relative

.navigation
  position: absolute
  bottom: 0
  right: 0
  display: flex
  flex-direction: row-reverse
</style>
