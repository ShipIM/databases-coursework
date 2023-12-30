<template>
  <table id="frame">
    <tr>
      <th @click="handleNavigate" id="header"><b>Registration</b></th>
    </tr>
    <tr>
      <td>
        <register-form :destination="'http://localhost:8080/auth/registration'" :limit="40" @fetch="parse"/>
        <p>
          <router-link to="/login">Already have an account?</router-link>
        </p>
      </td>
    </tr>
    <tr v-if="errors != null">
      <td>
        <p v-for='error in errors' :key="error" class="error">
          {{error}}
        </p>
      </td>
    </tr>
  </table>
</template>

<script>
import {router} from "@/router";
import {getCookie} from "@/cookies";
import registerForm from "@/components/register/register-form";

export default {
  components: {
    registerForm
  },
  data() {
    return {
      errors: null
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
        router.back();
      } else if (response.status === 404) {
        this.errors = ["There is no account with the specified email address"];
      } else if (response.status === 403) {
        this.errors = ["The password is incorrect"];
      } else if (response.status === 400) {
        response.text().then((text) => {
          const errors = JSON.parse(text).errors;
          this.errors = Array.isArray(errors) ? errors : [errors];
        })
      }
    },
    handleNavigate() {
      router.push('/main');
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