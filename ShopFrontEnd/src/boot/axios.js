import Vue from "vue";
import axios from "axios";
require("promise.prototype.finally").shim();

if (process.env.DEV) {
    axios.defaults.baseURL = "/project";
}

Vue.prototype.$axios = axios;