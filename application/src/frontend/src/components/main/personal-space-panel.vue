<template>
  <div v-if="typeof(username) !== 'undefined'">
    <span @click="handleUsernameClick">{{username}}</span>
    <button @click="handleLogOutClick">Log out</button>
  </div>
  <div v-if="typeof(username) === 'undefined'">
    <button @click="handleLogInClick">Log in</button>
  </div>
</template>

<script>
import {getCookie, eraseCookie} from "@/cookies";
import {router} from "@/router";

export default {
  data() {
    return {
      username: getCookie('email') === null ?
          undefined : getCookie('email').replace('@*', '')
    }
  },
  methods: {
    handleUsernameClick() {
      //TODO после того, как доработаю страницу ЛК, надо добавить сюда переход
    },
    handleLogOutClick() {
      eraseCookie('token');
      eraseCookie('email');

      this.username = null;
    },
    handleLogInClick() {
      router.push('/login');
    }
  }
}
</script>