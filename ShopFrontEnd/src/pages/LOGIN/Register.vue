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
    async next() {
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
        try {
          let res = await this.$request.userRegister(
            this.info.account,
            this.info.name,
            this.info.password
          );
          if (!res.data.errcode) {
            console.log(res);
            this.$store.commit("Common/user", res.data.data);
            this.$router.push("/");
          } else {
            this.$q.dialog({
              message: res.data.message
            });
          }
        } catch (e) {
          this.$q.dialog({
            message: String(e)
          });
        }
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
