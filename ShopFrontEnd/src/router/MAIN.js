export default {
    path: "/",
    component: () =>
        import ("layouts/MainLayout.vue"),
    children: [{
            path: "",
            component: () =>
                import ("pages/MAIN/Home.vue")
        },
        {
            path: "comment",
            component: () =>
                import ("pages/MAIN/Comment.vue")
        }
    ]
};