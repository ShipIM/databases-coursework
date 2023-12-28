<template>
  <form @submit.prevent>
    <table>

      <colgroup>
        <col width="50%">
      </colgroup>

      <tr>
        <td class="label"><label for="username">Укажите почту:</label></td>
        <td class="input"><input type="text" id="username" :maxlength="limit" v-model="username"></td>
      </tr>
      <tr>
        <td class="label"><label for="password">Введите пароль:</label></td>
        <td class="input"><input type="password" id="password" :maxlength="limit" v-model="password"></td>
      </tr>
      <tr>
        <td class="label"><label for="birth_date">Укажите дату рождения:</label></td>
        <td class="input"><input type="text" id="birth_date" :maxlength="limit" v-model="birthDate"></td>
      </tr>
      <tr>
        <td class="confirm" colspan="2">
          <button :disabled="disabled" @click="submit">Зарегистрироваться</button>
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
      birthDate:null,

      passwordInvalid: true,
      usernameInvalid: true,
      birthDateInvalid: true
    }
  },
  emits: ['fetch'],
  computed: {
    disabled() {
      return this.passwordInvalid || this.usernameInvalid || this.birthDateInvalid;
    }
  },
  methods: {
    validateEmail(value) {
      return /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value.toLowerCase());
    },
    validatePassword(value) {
      return value.length >= 8 && value.length <= 16;
    },
    validateDate(value) {
      return /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/.test(value);
    },
    submit() {
      fetch(this.destination, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({email: this.username, password: this.password, birth_date: this.birthDate})
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
    },
    'birthDate'(newValue) {
      newValue = newValue.trim();
      this.birthDate = newValue

      this.birthDateInvalid = !this.validateDate(newValue);
    }
  }
}
</script>

<style scoped>
table {
  width: 100%;
}

.input {
  text-align: left;
  padding: 1% 1% 1% 1%;
}

.label {
  text-align: right;
  padding: 1% 1% 1% 1%;
}

.confirm {
  text-align: center;
  padding: 2% 1% 1% 1%;
}
</style>