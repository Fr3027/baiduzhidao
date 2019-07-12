import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import SecondHand from "./views/SecondHand.vue";
import ShareCar from "./views/ShareCar.vue";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "about" */ "./views/About.vue")
    },
    {
      path: "/second",
      name: "second",
      component: SecondHand
    },
    {
      path: "/sharecar",
      name: "sharecar",
      component: ShareCar
    }
  ]
});
