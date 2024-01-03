import {createRouter, createWebHistory} from "vue-router"
import loginPage from "@/components/login/login-page";
import registerPage from "@/components/register/register-page";
import mainPage from "@/components/main/main-page";
import personalSpacePage from "@/components/main/personal-space-page";
import lotPage from "@/components/lots/lots-page"

const routes = [
    {path: '/login', component: loginPage, name: 'login'},
    {path: '/register', component: registerPage, name: 'register'},
    {path: '/main', component: mainPage, name: 'main'},
    {path: '/personal', component: personalSpacePage, name: 'personal'},
    {path: '/lots/:id', component: lotPage, name: 'lots'}
];

export const router = createRouter({
    history: createWebHistory(),
    routes
})
