<template>
  <section class="q-pa-xl">
    <q-btn @click="$router.push('/')">返回</q-btn>

    <q-input v-model="comment.info" placeholder="在此留言">
      <template v-slot:append>
        <q-icon name="send" @click="addComment()" class="cursor-pointer" />
      </template>
    </q-input>
    <div class="row comment-item" v-for="(comment,index) of comments.data" :key="'comment'+index">
      <div>ID: {{comment.id}}</div>
      <div v-html="'Info: '+comment.info"></div>
      <div>Time: {{comment.time}}</div>
    </div>
  </section>
</template>
<script>
export default {
  name: "comment",
  computed: {
    comments() {
      let res = Object.assign({}, this.rowComments);
      if (res.data && res.data.length) {
        for (let comment of res.data) {
          comment.time = new Date(Number(comment.time)).toDateString();
        }
      }
      return res;
    }
  },
  methods: {
    getComments(vm) {
      vm = vm || this;
      vm.$axios.get("/comment").then(res => {
        this.rowComments = res.data;
      });
    },
    addComment() {
      this.$axios
        .post("/comment", {
          id: this.comment.id,
          info: this.comment.info
        })
        .then(res => {
          console.log(res);
          if (res.errcode) {
            this.$q.dialog({
              message: res.message
            });
          } else {
            this.getComments();
          }
          this.comment.info = "";
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.getComments(vm);
    });
  },
  data() {
    return {
      rowComments: Object,
      comment: {
        id: sessionStorage.getItem("id"),
        info: ""
      }
    };
  }
};
</script>
<style lang="sass" scoped>
.comment-item
  margin: 20px 0
  div
    width: 100%
</style>
