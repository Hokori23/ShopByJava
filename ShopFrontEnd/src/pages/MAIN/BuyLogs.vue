<template>
  <section class="q-py-md page">
    <div class="q-px-xl row justify-end non-selectable" style="width:100%">
      <q-badge color="teal" class="q-mx-sm q-py-sm">
        起始时间: {{time.start}}
        <q-btn icon="event" round color="primary" class="q-ml-xs">
          <q-popup-proxy
            @before-show="updateStartDateProxy"
            transition-show="scale"
            transition-hide="scale"
          >
            <q-date v-model="proxy.start">
              <div class="row items-center justify-end q-gutter-sm">
                <q-btn label="Cancel" color="primary" flat v-close-popup />
                <q-btn label="OK" color="primary" flat @click="saveStartDate" v-close-popup />
              </div>
            </q-date>
          </q-popup-proxy>
        </q-btn>
      </q-badge>
      <q-badge color="teal" class="q-mx-sm q-py-sm">
        终止时间: {{time.end}}
        <q-btn icon="event" round color="primary" class="q-ml-xs">
          <q-popup-proxy
            @before-show="updateEndDateProxy"
            transition-show="scale"
            transition-hide="scale"
          >
            <q-date v-model="proxy.end">
              <div class="row items-center justify-end q-gutter-sm">
                <q-btn label="Cancel" color="primary" flat v-close-popup />
                <q-btn label="OK" color="primary" flat @click="saveEndDate" v-close-popup />
              </div>
            </q-date>
          </q-popup-proxy>
        </q-btn>
      </q-badge>
      <q-btn color="primary" class="q-mx-sm" @click="setRange(false)">查看全部</q-btn>
      <q-btn color="primary" class="q-mx-sm" @click="setRange(true)">确定</q-btn>
    </div>
    <div v-if="log.length" class="q-py-sm q-ma-sm" style="border-radius:3px;border:solid 2px #ccc">
      <div
        class="q-px-xl row items-center"
        style="height:8%;min-height:80px;border-bottom:1px solid #ccc"
        v-for="(item,index) in log"
        :key="'log-'+index"
      >
        <div class="column" style="flex:1 0 200px">
          <div>商品名：{{item.product_name}}</div>
        </div>
        <div class="column" style="flex:1 1 auto">
          <div>分类：{{item.category}}</div>
        </div>
        <div class="column" style="flex:1 1 auto">
          <div>单价：{{item.price}}</div>
        </div>
        <div class="column" style="flex:1 1 auto">
          <div>数量：{{item.count}}</div>
        </div>
        <div class="column" style="flex:1 1 auto">
          <div>总价：{{item.price*item.count}}</div>
        </div>
        <div class="column" style="flex:1 1 auto">
          <div>购买时间：{{formatTimeStamp(item.time).toString()}}</div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
export default {
  name: "buyLogs",
  methods: {
    async productLogQueryAll() {
      let res = await this.$request.productLogQueryAll();
      if (this.$request.dealErr(res,this)) {
        this.log = res.data.data.sort((b, a) => {
          return Number(a.time) - Number(b.time);
        });
      }
    },
    async productLogQuery() {
      let res = await this.$request.productLogQuery(
        new Date(this.time.start).getTime(),
        new Date(this.time.end).getTime()
      );
      if (this.$request.dealErr(res,this)) {
        this.log = res.data.data.sort((b, a) => {
          return Number(a.time) - Number(b.time);
        });
      }
    },
    updateStartDateProxy() {
      this.proxy.start = this.time.start;
    },
    updateEndDateProxy() {
      this.proxy.end = this.time.end;
    },
    saveStartDate() {
      this.time.start = this.proxy.start;
    },
    saveEndDate() {
      this.time.end = this.proxy.end;
    },
    formatTimeStamp(timeStamp) {
      let time = null;
      if (timeStamp) {
        time = new Date(Number(timeStamp));
      } else {
        time = new Date();
      }
      let tempMonth = time.getMonth() + 1;
      let tempDate = time.getDate();
      if (tempMonth < 10) {
        tempMonth = "0" + tempMonth;
      }
      if (tempDate < 10) {
        tempDate = "0" + tempDate;
      }

      return {
        year: time.getFullYear(),
        month: tempMonth,
        date: tempDate,
        hour: time.getHours(),
        minute: time.getMinutes(),
        toString: function() {
          return `${this.year}/${this.month}/${this.date} ${this.hour}:${this.minute}`;
        },
        format1: function() {
          return `${this.year}/${this.month}/${this.date}`;
        }
      };
    },
    setRange(val) {
      if (val && (!this.time.start || !this.time.end)) {
        this.isRange = val;
        this.$q.dialog({
          message:'请选择起始时间和终止时间'
        })
      }
      this.isRange = val;
    }
  },
  computed: {
    DateStrTotimeStamp() {
      return date => {
        date.start = new Date(date.start).getTime();
        date.end = new Date(date.end).getTime();
        return date;
      };
    }
  },
  watch: {
    isRange(val) {
      if (val) {
        this.productLogQuery();
      } else {
        this.productLogQueryAll();
      }
    }
  },
  data() {
    return {
      time: {
        start: "",
        end: ""
      },
      proxy: {
        start: "",
        end: ""
      },
      isRange: false,
      log: []
    };
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.productLogQueryAll();
    });
  }
};
</script>
