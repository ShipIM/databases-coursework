import {createRouter, createWebHistory} from "vue-router"
import loginPage from "@/components/login-page";
import registerPage from "@/components/register-page";

const routes = [
    {path: '/login', component: loginPage, name: 'login'},
    {path: '/register', component: registerPage, name: 'register'}
];

export const router = createRouter({
    history: createWebHistory(),
    routes
})
