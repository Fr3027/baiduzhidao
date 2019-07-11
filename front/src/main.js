import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import './plugins/vuetify.js'
import Vuetify from 'vuetify' // Import Vuetify to your project
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.use(Vuetify) // Add Vuetify as a plugin

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
