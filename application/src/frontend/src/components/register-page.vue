<template>
  <table id="frame">
    <tr>
      <th id="header"><b>ФИО: Шипунов Илья Михайлович. Группа: P32111 Вариант: 11106</b></th>
    </tr>
    <tr>
      <td>
        <register-form :destination="'http://localhost:8080/auth/registration'" :action="'Зарегистрироваться'" :limit="40" @fetch="parse"/>
        <p>
          <router-link to="/login">Уже есть аккаунт?</router-link>
        </p>
      </td>
    </tr>
    <tr v-if="error != null">
      <td>
        <p class="error">{{ error }}</p>
      </td>
    </tr>
  </table>
</template>

<script>
import {router} from "@/router";
import {getCookie} from "@/cookies";
import registerForm from "@/components/register-form";

export default {
  components: {
    registerForm
  },
  data() {
    return {
      error: null
    }
  },
  beforeRouteEnter(to, from, next) {
    if (getCookie("token") != null)
      next("main");
    else next();
  },
  methods: {
    parse(response) {
      if (response.ok) {
        router.push({path: "/login"})
      } else if (response.status === 404) {
        this.errors = ["Аккаунта с указанной почтой не существует"];
      } else if (response.status === 403) {
        this.errors = ["Указан неверный пароль"];
      } else if (response.status === 400) {
        response.text().then((text) => {
          this.errors = JSON.parse(text).errors;
        })
      }
    }
  }
}
</script>

<style scoped>
@media (width < 877px) {
  #frame {
    width: 400px;
  }
}

@media (877px <= width < 1062px) {
  #frame {
    width: 600px;
  }
}

@media (1062px <= width) {
  #frame {
    width: 800px;
  }
}

#frame {
  margin: auto;
  height: auto;
  text-align: justify;
}

#header {
  color: black;
  font-size: medium;
  font-family: serif;
  text-align: center;
}

td p {
  padding-left: 3%;
  padding-right: 3%;
}

.error {
  color: red;
  font-size: medium;
  padding-left: 3%;
  padding-right: 3%;
  font-family: 'Times New Roman', Times, serif;
  text-align: center;
}

p {
  text-align: center;
}

tr, td, th {
  border-radius: 12px;
  background-color: wheat;
}
</style>