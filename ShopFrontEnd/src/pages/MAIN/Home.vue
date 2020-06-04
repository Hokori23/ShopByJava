<template>
  <section class="page">
    <q-select
      class="q-my-md q-px-xl"
      v-model="nowCategory"
      :options="category"
      label="分类"
      style="margin-left:auto;min-width:300px;  max-width:400px"
    />
    <q-list class="row q-px-xl">
      <q-card
        style="width:45%;margin:5% 2.5%"
        v-for="(item,i) of currentProducts"
        :key="'product-'+i"
      >
        <img src="statics/chicken-salad.jpg"/>

        <q-list>
          <q-item clickable>
            <q-item-section avatar>
              <q-icon color="primary" name="local_bar" />
            </q-item-section>

            <q-item-section>
              <q-item-label>{{item.name}}</q-item-label>
              <q-item-label caption>{{item.description}}</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable>
            <q-item-section avatar>
              <q-icon color="red" name="attach_money" />
            </q-item-section>

            <q-item-section>
              <q-item-label>价格</q-item-label>
              <q-item-label caption>{{item.price}}</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable>
            <q-item-section avatar>
              <q-icon color="amber" name="local_movies" />
            </q-item-section>

            <q-item-section>
              <q-item-label>分类</q-item-label>
              <q-item-label caption>{{item.category}}</q-item-label>
            </q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section avatar>
              <q-icon color="success" name="cart" />
            </q-item-section>

            <q-item-section>
              <q-item-label>添加购物车</q-item-label>
              <q-item-label caption>
                <q-btn round color="primary" size="xs" icon="remove" @click="reduce(item)" />
                <q-chip outline color="primary" text-color="white" :label="item.count"></q-chip>
                <q-btn round color="primary" size="xs" icon="add" @click="add(item)" />
                <q-btn
                  round
                  color="primary"
                  size="sm"
                  icon="shopping_cart"
                  @click="append(item)"
                  class="q-ml-md"
                />
              </q-item-label>
            </q-item-section>
          </q-item>
        </q-list>
      </q-card>
    </q-list>
    <q-pagination
      v-model="currentPage"
      color="purple"
      :max="pageSum(nowCategory)"
      :max-pages="6"
      :boundary-numbers="true"
    ></q-pagination>
  </section>
</template>
<script>
export default {
  name: "home",
  computed: {
    category() {
      let arr = [""];
      this.productInfo &&
        this.productInfo.products &&
        this.productInfo.products.map(item => {
          if (arr.indexOf(item.category) == -1) {
            arr.push(item.category);
          }
        });
      return arr;
    },
    pageSum() {
      return val => {
        let lastSum;
        if (val) {
          let sum = 0;
          this.productInfo &&
            this.productInfo.products &&
            this.productInfo.products.map(item => {
              if (item.category === val) {
                sum++;
              }
            });
          lastSum = sum;
        } else {
          lastSum = this.productInfo.sum;
        }
        return Math.ceil(lastSum / this.capacity);
      };
    }
  },
  data() {
    return {
      welcome: "",
      back: false,
      info: {
        name: sessionStorage.getItem("name")
      },
      valid: {
        name: true,
        password: true
      },
      text: {
        name: this.$t("login.nickNameErr"),
        password: this.$t("login.passwordErr")
      },
      productInfo: Object,
      currentPage: 1,
      capacity: 4,
      currentProducts: [],
      isCategory: false,
      nowCategory: this.category && this.category.length && this.category[0]
    };
  },
  methods: {
    append(item) {
      this.$store.commit("MainLayout/appendCart", item);
      this.$q.notify({
        message:'添加成功',
        timeout:250
      });
    },
    add(item) {
      item.count++;
    },
    reduce(item) {
      item.count > 1 ? item.count-- : false;
    },
    async productQueryInPage() {
      console.log("query by page");
      let res = await this.$request.productQueryInPage(
        this.currentPage,
        this.capacity
      );
      console.log(res.data);
      if (res.data.errcode) {
        this.$q
          .dialog({
            message: res.data.message
          })
          .onDismiss(() => {
            if (res.data.errcode > 400) {
              this.$router.push("/login");
            }
          });
        return;
      }
      this.currentProducts = res.data.data;
      this.currentProducts.map(item => {
        this.$set(item, "count", 1);
      });
    },
    async productQueryInPageAndCategory() {
      console.log("query by page&category");
      let res = await this.$request.productQueryInPageAndCategory(
        this.currentPage,
        this.capacity,
        this.nowCategory
      );

      if (res.data.errcode) {
        this.$q
          .dialog({
            message: res.data.message
          })
          .onDismiss(() => {
            if (res.data.errcode > 400) {
              this.$router.push("/login");
            }
          });
        return;
      }
      this.currentProducts = res.data.data;
      this.currentProducts.map(item => {
        this.$set(item, "count", 1);
      });
    }
  },
  watch: {
    currentPage(val) {
      console.log("currentPage", val, "isCategory", this.isCategory);
      if (this.isCategory) {
        this.productQueryInPageAndCategory();
      } else {
        this.productQueryInPage();
      }
    },
    nowCategory(val) {
      console.log("nowCategory", val);
      if (val) {
        this.isCategory = true;
      } else {
        this.isCategory = false;
      }
      if (this.isCategory) {
        this.currentPage = 1;
        this.productQueryInPageAndCategory();
      } else {
        this.currentPage = 1;
        this.productQueryInPage();
      }
    },
    isCategory(val) {
      console.log("is category", val);
    }
  },
  //Home.vue
  beforeRouteEnter(to, from, next) {
    //判断路由提供页面提示
    next(async vm => {
      let res = await vm.$request.productQueryAll();
      if (!res.errcode) {
        vm.productInfo = res.data;
      } else {
        vm.$q.dialog({
          message: res.data.message
        });
      }
      vm.productQueryInPage();
    });
  }
};
</script>
<style lang="sass" scoped>
</style>
<style lang="sass">
.q-pagination
  align-self: flex-end
  bottom: 55px
  width: 100%
  div
    margin: 0 auto

.page
  position: relative
  display: flex
  flex-direction: column
  padding-bottom: 43px
</style>
