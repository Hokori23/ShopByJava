export default {
    path: "/",
    component: () =>
        import ("layouts/MainLayout.vue"),
    children: [{
            path: "",
            meta: {
                index: 0
            },
            component: () =>
                import ("pages/MAIN/Home.vue")
        },
        {
            path: "cart",
            meta: {
                index: 1
            },
            component: () =>
                import ("pages/MAIN/Cart.vue")
        },
        {
            path: "log",
            meta: {
                index: 2
            },
            component: () =>
                import ("pages/MAIN/BuyLogs.vue")
        },
        {
            path: "user",
            meta: {
                index: 1,
                keepAlive: true
            },
            component: () =>
                import ("pages/MAIN/User.vue")
        }, {
            path: 'admin',
            meta: {
                index: 2,
                keepAlive: true
            },
            component: () =>
                import ("pages/MAIN/Admin.vue")
        }
    ]
};