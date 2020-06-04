<template>
  <q-page class="column items-center q-page" :class="{ init: init }">
    <div class="banner justify-center row items-end">
      <q-avatar :class="{ 'shadow-3': init, 'shadow-10': !init }" class="avatar">
        <img src="../../statics/icons/icon-128x128.png" />
      </q-avatar>
    </div>

    <transition appear enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
      <!-- 输入框 -->
      <div class="input items-center column justify-between" v-show="init">
        <div class="loginAbout items-center column">
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

          <!-- 忘记密码 -->
          <q-btn
            flat
            color="primary"
            :label="$t('login.forgot')"
            class="self-end q-px-sm"
            dense
            @click="$router.push('/login/forgot')"
          />
          <!-- 登录 -->
          <q-btn
            color="primary"
            :label="$t('login.login')"
            rounded
            unelevated
            style="width:50vw;max-width:200px"
            class="q-mt-md"
            @click="login()"
          />
          <div class="text-primary q-pt-md">{{$t('login.visitNum')}} : {{count}}</div>
        </div>

        <!-- 注册 -->
        <div class="register">
          <div class="row self-center register--box justify-center">
            <div class="column justify-center">{{ $t('login.noAccountYet') }}</div>
            <q-btn
              color="primary"
              :label="$t('login.register')"
              dense
              class="q-mx-xs q-px-sm"
              flat
              @click="$router.push('/login/register')"
            />

            <q-btn round color="primary" icon="g_translate" class="lang" dense>
              <q-menu>
                <q-list style="min-width: 100px">
                  <q-item clickable v-close-popup @click="toChinese()">
                    <q-item-section>中文简体</q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="toEnglish()">
                    <q-item-section>English</q-item-section>
                  </q-item>
                </q-list>
              </q-menu>
            </q-btn>
          </div>
        </div>
      </div>
    </transition>
  </q-page>
</template>

<script>
// import languages from "quasar/lang/index.json";
export default {
  name: "login",
  data() {
    return {
      info: {
        account: "",
        password: ""
      },
      init: false,
      valid: {
        account: true,
        password: true
      },
      text: {
        account: this.$t("login.accountErr"),
        password: this.$t("login.passwordErr")
      },
      lang: this.$q.lang.isoName,
      count: 0
    };
  },
  methods: {
    async login() {
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
      if (flag) {
        //Login.vue
        //登录请求
        try {
          let res = await this.$request.userLogin(
            this.info.account,
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
    },
    clear(value) {
      this.valid[value] = true;
    },
    toChinese() {
      if (this.$i18n.locale !== "zh-cn") {
        this.$i18n.locale = "zh-cn";
      }
    },
    toEnglish() {
      if (this.$i18n.locale !== "en-us") {
        this.$i18n.locale = "en-us";
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$emit("header", false);
    });
  },
  async mounted() {
    (function(vm) {
      setTimeout(() => {
        vm.init = true;
      }, 1500);
    })(this);
    let res = await this.$request.countPlus();
    this.count = res.data.data;
  }
};
</script>

<style lang="sass" scoped>
.q-page
  width: 100%
  overflow: hidden

  .avatar
    width: 80px
    height: 80px
    transform: scale(1.5) translateY(45%)
    transition: transform .2s ease-out, box-shadow .5s
    animation: 1s bound forwards
    animation-delay: .7s
    z-index: 999

  .banner
    width: 100%
    height: 80vh
    margin-bottom: 54px
    position: relative
    transition: height .2s ease-out
    animation: 1s spread forwards
    animation-delay: .5s
    background-image: url(../../statics/18.jpg)
    background-size: 100% auto

  .banner::after
    content: ''
    bottom: 0
    height: 25vh
    z-index: 1
    width: 115%
    position: absolute
    left: 50%
    transform: translateX(-50%)
    border-radius: 0 0 50% 50%
    background-color: transparent
    box-shadow: 0 2020px 0 2000px #ffffff

  &.init
    .avatar
      animation: none
      transform: scale(1) translateY(25%)
    .banner
      animation: none
      height: 25vh
      margin-bottom: 20px

  .input
    width: 100%
    z-index: 999
    height: calc(75vh - 20px)

  .register
    justify-items: flex-end
    margin-bottom: 24px

    .register--box
      position: relative
      height: 100%
      width: 100%

      .lang
        position: absolute
        right: 0
        bottom: 0
        transform: translateX(100%)

  @keyframes bound
    0%
      transform: scale(1.5) translateY(45%)

    50%
      transform: scale(1) translateY(50%)

    100%
      transform: scale(1) translateY(25%)

  @keyframes spread

    0%
      height: 80vh
      margin-bottom: 54px

    15%
      height: 85vh

    70%
      height: 20vh

    100%
      height: 25vh
      margin-bottom: 20px
</style>
