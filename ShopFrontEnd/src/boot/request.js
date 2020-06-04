import Vue from "vue";
import axios from "axios";

const request = {
    userLogin: (id, password) => {
        return new Promise((resolve, reject) => {
            axios
                .post("/user", {
                    id: id,
                    password: password
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    userLogOut: () => {
        return new Promise((resolve, reject) => {
            axios
                .get("/user/logout")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    userRegister: (id, name, password) => {
        return new Promise((resolve, reject) => {
            axios
                .post("/user", {
                    id: id,
                    name: name,
                    password: password
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    userQuery: id => {
        return new Promise((resolve, reject) => {
            axios
                .get("/user", {
                    params: { id: id }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    usersQuery: () => {
        return new Promise((resolve, reject) => {
            axios
                .get("/user")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    userCancel: password => {
        return new Promise((resolve, reject) => {
            axios
                .delete("/user", {
                    params: {
                        password: password
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    userEdit: (name, password, old_password) => {
        return new Promise((resolve, reject) => {
            axios
                .put("/user", {
                    name: name,
                    password: password,
                    old_password: old_password
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productCreate: (name, description, price, category) => {
        return new Promise((resolve, reject) => {
            axios
                .post("/product", {
                    name: name,
                    description: description,
                    price: price,
                    category: category
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productRemove: id => {
        return new Promise((resolve, reject) => {
            axios
                .delete("/product", {
                    params: {
                        id: id
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productEdit: (id, name, description, price, category) => {
        console.log(arguments)
        return new Promise((resolve, reject) => {
            axios
                .put("/product", {
                    id: id,
                    name: name,
                    description: description,
                    price: price,
                    category: category
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productQueryAll: () => {
        console.log("queryAll");
        return new Promise((resolve, reject) => {
            axios
                .get("/product")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productQueryInPage: (page, capacity) => {
        return new Promise((resolve, reject) => {
            axios
                .get("/product", {
                    params: {
                        page: page,
                        capacity: capacity
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productQueryInPageAndCategory: (page, capacity, category) => {
        return new Promise((resolve, reject) => {
            axios
                .get("/product", {
                    params: {
                        page: page,
                        capacity: capacity,
                        category: category
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productBuy: products => {
        return new Promise((resolve, reject) => {
            axios
                .post("/productlog", {
                    products: products
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productLogQueryAll: () => {
        return new Promise((resolve, reject) => {
            axios
                .get("/productlog")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    productLogQuery: (start_time, end_time) => {
        return new Promise((resolve, reject) => {
            axios
                .get("/productlog", {
                    params: {
                        start_time: start_time,
                        end_time: end_time
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    countPlus: () => {
        return new Promise((resolve, reject) => {
            axios
                .post("/count")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    countQuery: () => {
        return new Promise((resolve, reject) => {
            axios
                .get("/count")
                .then(res => {
                    resolve(res);
                })
                .catch(e => {
                    reject(e);
                });
        });
    },
    dealErr: (res, vm) => {
        let flag = true;
        if (res.data.errcode) {
            flag = false;
            vm.$q
                .dialog({
                    message: res.data.message
                })
                .onDismiss(() => {
                    if (res.data.errcode == 401) {
                        vm.$router.push("/login");
                    }
                });
        }
        return flag;
    }
};
Vue.prototype.$request = request;