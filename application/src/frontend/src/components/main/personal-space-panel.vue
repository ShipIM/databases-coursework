<template>
  <div v-if="typeof(username) !== 'undefined'" class="login1">
    <span @click="handleUsernameClick">{{username}}</span>
    <button v-if="$route.name === 'personal'" @click="handleLogOutClick" class="logout">Log out</button>
  </div>
  <div v-if="typeof(username) === 'undefined'">
    <button @click="handleLogInClick" class="login">Log in</button>
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
      router.push('/personal');
    },
    handleLogOutClick() {
      eraseCookie('token');
      eraseCookie('email');

      this.username = null;

      router.push('/login');
    },
    handleLogInClick() {
      router.push('/login');
    }
  }
}
</script>
<style scoped>
.login {
  background-color: blue;
  color: rgb(182, 168, 45);
  padding-right: 10px;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}

.login1 {
  color: rgb(255, 255, 255);
  padding-left: 10px;
}
.logout {
  background-color: blue;
  color: rgb(182, 168, 45);
  padding-right: 10px;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}






</style>