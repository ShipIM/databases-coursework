import {createRouter, createWebHistory} from "vue-router"
import loginPage from "@/components/login/login-page";
import registerPage from "@/components/register/register-page";
import mainPage from "@/components/main/main-page";

const routes = [
    {path: '/login', component: loginPage, name: 'login'},
    {path: '/register', component: registerPage, name: 'register'},
    {path: '/main', component: mainPage, name: 'main'}
];

export const router = createRouter({
    history: createWebHistory(),
    routes
})
