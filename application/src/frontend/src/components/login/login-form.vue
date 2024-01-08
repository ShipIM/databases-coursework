<template>
  <form @submit.prevent>
    <table>

      <colgroup>
        <col width="50%">
      </colgroup>

      <tr>
        <td class="label"><label for="username">Email:</label></td>
        <td class="input"><input type="text" id="username" :maxlength="limit" v-model="username"></td>
      </tr>
      <tr>
        <td class="label"><label for="password">Password:</label></td>
        <td class="input"><input type="password" id="password" :maxlength="limit" v-model="password"></td>
      </tr>
      <tr>
        <td class="confirm" colspan="2">
          <button :disabled="disabled" @click="submit" class="login2">Log in</button>
        </td>
      </tr>
    </table>
  </form>
</template>

<script>

export default {
  props: {
    destination: String,

    limit: Number
  },
  data() {
    return {
      username: null,
      password: null,

      passwordInvalid: true,
      usernameInvalid: true
    }
  },
  emits: ['fetch'],
  computed: {
    disabled() {
      return this.passwordInvalid || this.usernameInvalid;
    }
  },
  methods: {
    validateEmail(value) {
      return /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value.toLowerCase());
    },
    validatePassword(value) {
      return value.length >= 8 && value.length <= 16;
    },
    submit() {
      fetch(this.destination, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({email: this.username, password: this.password})
      }).then((response) => this.update(response));
    },
    update(data) {
      this.$emit('fetch', data);
    }
  },
  watch: {
    'password'(newValue) {
      newValue = newValue.trim();
      this.password = newValue

      this.passwordInvalid = !this.validatePassword(newValue);
    },
    'username'(newValue) {
      newValue = newValue.trim();
      this.username = newValue

      this.usernameInvalid = !this.validateEmail(newValue);
    }
  }
}
</script>

<style scoped>
table {
  width: 100%;
}

.input {
  margin-top: 0px;
  border: 10;
  border-color: rgb(171, 179, 179);
  color: aliceblue;
  outline: none;
  margin-left: 50px;
  border-radius: 4px;
}

.label {
  text-align: right;
  padding: 1% 1% 1% 1%;
  margin-top: 0px;
  color: rgb(255, 255, 255);
  outline: none;
  margin-left: 50px;
  border-radius: 4px;
}

.confirm {
  text-align: center;
  padding: 2% 1% 1% 1%;
}
.login2 {
  background-color: blue;
  color: rgb(182, 168, 45);
  padding-right: 10px;
  border-radius: 4px;
  margin-left: 10px;
  padding: 2px 10px;
  border-style: inset;
  border-color: black;
}
</style>