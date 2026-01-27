import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import App from './App.vue';
import router from './router';
import './style.css';

import Menubar from 'primevue/menubar';
import Button from 'primevue/button';
import Menu from 'primevue/menu';

const app = createApp(App);

app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
});

app.component('Menubar', Menubar);
app.component('Button', Button);
app.component('Menu', Menu);

app.use(router);
app.mount('#app');