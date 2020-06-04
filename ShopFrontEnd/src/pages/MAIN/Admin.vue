<template>
  <section class="page">
    <q-list class="list-container non-selectable" :bordered="!$q.screen.lt.md">
      <q-item>
        <q-item-section avatar>
          <q-icon name="add" />
        </q-item-section>
        <q-item-section>
          <q-item-label>增加商品</q-item-label>
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="商品名" v-model="add.name" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="描述" v-model="add.description" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="单价" v-model="add.price" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="分类" v-model="add.category" />
        </q-item-section>
      </q-item>
      <q-item>
        <q-item-section side style="margin-left:auto">
          <q-btn @click='addRequest'>添加</q-btn>
        </q-item-section>
      </q-item>

      <q-separator inset />

      <q-item>
        <q-item-section avatar>
          <q-icon name="remove" />
        </q-item-section>
        <q-item-section>
          <q-item-label>删除商品</q-item-label>
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="商品ID" v-model="remove.id" />
        </q-item-section>
      </q-item>
      <q-item>
        <q-item-section side style="margin-left:auto">
          <q-btn @click='removeRequest'>删除</q-btn>
        </q-item-section>
      </q-item>

      <q-item>
        <q-item-section avatar>
          <q-icon name="chrome_reader_mode" />
        </q-item-section>
        <q-item-section>
          <q-item-label>编辑商品</q-item-label>
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="商品ID" v-model="put.id" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="商品名" v-model="put.name" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="描述" v-model="put.description" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="单价" v-model="put.price" />
        </q-item-section>
      </q-item>
      <q-item clickable v-ripple>
        <q-item-section>
          <q-input type="text" label="分类" v-model="put.category" />
        </q-item-section>
      </q-item>
      <q-item>
        <q-item-section side style="margin-left:auto">
          <q-btn @click='putRequest()'>编辑</q-btn>
        </q-item-section>
      </q-item>
    </q-list>
  </section>
</template>
<script>
export default {
  name: "admin",
  data() {
    return {
      add: {
        name: "",
        description: "",
        price: "",
        category: ""
      },
      remove: {
        id: ""
      },
      put: {
        id: "",
        name: "",
        description: "",
        price: "",
        category: ""
      }
    };
  },
  methods: {
    async addRequest() {
      console.log(this.add)
      if (!this.add.name || !this.add.price) {
        this.$q.dialog({
          message: "商品名和单价必填"
        });
        return;
      }
      let res = await this.$request.productCreate(
        this.add.name,
        this.add.description,
        this.add.price,
        this.add.category
      );
      if (this.$request.dealErr(res,this)) {
        this.$q.dialog({
          message: res.data.message
        });
      }
    },
    async removeRequest() {
      if (!this.remove.id) {
        this.$q.dialog({
          message: "商品ID必填"
        });
        return;
      }
      let res = await this.$request.productRemove(this.remove.id);
      console.log(res)
      if (this.$request.dealErr(res,this)) {
        this.$q.dialog({
          message: res.data.message
        });
      }
    },
    async putRequest() {
      if (!this.put.id || !this.put.name || !this.put.price) {
        this.$q.dialog({
          message: "商品名和单价必填"
        });
        return;
      }
      let res = await this.$request.productEdit(
        this.put.id,
        this.put.name,
        this.put.description,
        this.put.price,
        this.put.category,
      );
      console.log(this.put,res)
      if (this.$request.dealErr(res,this)) {
        this.$q.dialog({
          message: res.data.message
        });
      }
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

