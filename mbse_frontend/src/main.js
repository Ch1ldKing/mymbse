import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './rooter'
import store from './store'
import './api/mock'
import Cookie from 'js-cookie';

Vue.config.productionTip = false

Vue.use(ElementUI)
 
router.beforeEach((to, from, next) => {
  if (to.name !== 'login' &&  !store.state.tab.logged) {
    next({ name: 'login' });
  } else {
    next();
  }
});

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
 