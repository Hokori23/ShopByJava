<template>
  <section class="q-px-xl page cart-page">
    <h4 class="text-center non-selectable primary" v-if="!products||!products.length">购物车暂无商品</h4>
    <q-list class="row">
      <q-card
        class="q-my-sm q-mx-md"
        style="width:45%"
        v-for="(item,i) of products"
        :key="'product-'+i"
      >
        <img src="https://cdn.quasar.dev/img/parallax2.jpg" />

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
              <q-item-label>单价</q-item-label>
              <q-item-label caption>{{item.price}}</q-item-label>
            </q-item-section>
            <q-item-section>
              <q-item-label>总价格</q-item-label>
              <q-item-label caption>{{item.price*item.count}}</q-item-label>
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
              <q-icon color="amber" name="gavel" />
            </q-item-section>

            <q-item-section>
              <q-item-label>数量</q-item-label>
              <q-item-label caption>{{item.count}}</q-item-label>
              <q-checkbox v-model="item.choose" />
            </q-item-section>
          </q-item>
        </q-list>
      </q-card>
    </q-list>
    <div
      class="row sum-area justify-end items-center bg-grey-3"
      style="padding:0 5% 0 50%;width:100%;transform:translateX(-48px)"
    >
      <div class="self-center non-selectable text-primary">总价：</div>
      <q-chip class="non-selectable" size="md" :label="sum"></q-chip>
      <q-btn color="primary" class="q-ml-md self-center" @click="buy">购买</q-btn>
      <div></div>
    </div>
  </section>
</template>
<script>
export default {
  name: "cart",
  data() {
    return {
      products: null
    };
  },
  computed: {
    selected() {
      let arr = [];
      this.products &&
        this.products.map(item => {
          if (item.choose) {
            arr.push(item);
          }
        });
      return arr;
    },
    sum() {
      let sum = 0;
      this.selected.map(item => {
        sum += item.price * item.count;
      });
      return sum;
    }
  },
  methods: {
    async buy() {
      let arr = JSON.parse(JSON.stringify(this.selected));
      arr.map(item => {
        this.$set(item, "time", new Date().getTime());
        this.$set(item, "product_id", item.id);
        this.$delete(item, "id");
        this.$delete(item, "price");
        this.$delete(item, "category");
        this.$delete(item, "name");
        this.$delete(item, "description");
        this.$delete(item, "choose");
      });
      let res = await this.$request.productBuy(arr);
      console.log(res);
      this.$q.dialog({
        message: res.data.message
      });
      for (let i = 0; i < this.products.length; i++) {
        if (this.products[i].choose) {
          this.products.splice(i, 1);
          i--;
        }
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      let res = JSON.parse(JSON.stringify(vm.$store.state.MainLayout.cart));
      res.length > 0 &&
        res.map(item => {
          vm.$set(item, "choose", false);
        });
      vm.products = res;
    });
  }
};
</script>
<style lang="sass" scoped>
.cart-page
  height: calc(100vh - 50px - 48px)
  padding-bottom: 50px
  position: relative
.sum-area
  height: 50px
  position: fixed
  bottom: 48px
</style>
