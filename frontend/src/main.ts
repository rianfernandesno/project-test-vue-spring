import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';

import ToastService from 'primevue/toastservice';
import ConfirmationService from 'primevue/confirmationservice';

import App from './App.vue';
import router from './router';
import './style.css';

import Menubar from 'primevue/menubar';
import Button from 'primevue/button';
import Menu from 'primevue/menu';
import Dialog from 'primevue/dialog';
import Card from 'primevue/card';
import Tag from 'primevue/tag';
import Avatar from 'primevue/avatar';
import InputText from 'primevue/inputtext';
import IconField from 'primevue/iconfield';
import InputIcon from 'primevue/inputicon';
import FileUpload from 'primevue/fileupload';
import InputMask from 'primevue/inputmask';
import Textarea from 'primevue/textarea';
import Toast from 'primevue/toast';
import ConfirmDialog from 'primevue/confirmdialog';
import MultiSelect from 'primevue/multiselect';
import Checkbox from 'primevue/checkbox';

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Dropdown from 'primevue/dropdown';
import InputNumber from 'primevue/inputnumber';

const app = createApp(App);

app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            darkModeSelector: '.my-app-dark', // Opcional: ajuda a não quebrar o tema dark
        }
    }
});

app.use(ToastService);
app.use(ConfirmationService);
app.use(router);

// --- REGISTRO GLOBAL ---
app.component('Menubar', Menubar);
app.component('Button', Button);
app.component('Menu', Menu);
app.component('Dialog', Dialog);
app.component('Card', Card);
app.component('Tag', Tag);
app.component('Avatar', Avatar);
app.component('InputText', InputText);
app.component('IconField', IconField);
app.component('InputIcon', InputIcon);
app.component('FileUpload', FileUpload);
app.component('InputMask', InputMask);
app.component('Textarea', Textarea);
app.component('Toast', Toast);
app.component('ConfirmDialog', ConfirmDialog);
app.component('MultiSelect', MultiSelect);
app.component('Checkbox', Checkbox);

app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('Dropdown', Dropdown);
app.component('InputNumber', InputNumber); 

app.mount('#app');