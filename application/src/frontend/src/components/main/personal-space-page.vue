<template>
  <table id="frame">
    <tr>
      <th @click="handleNavigate" colspan='2' id="header"><b>Personal space</b></th>
    </tr>
    <tr>
      <td>
        <span>Name</span>
        <div>
          <input type="text" v-model="itemName">
        </div>
      </td>
      <td>
        <personal-space-panel />
      </td>
    </tr>
    <tr>
      <td>
        <span>Categories</span>
        <div class="list-wrapper">
          <table>
            <category-container v-for="category in categories"
                                :key="category"
                                :content="category"
                                @emit-category="handleChoice" />
          </table>
        </div>
      </td>
      <td>
        <item-table />
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <button @click="handleSearch">Search</button>
        <button @click="handleReset">Reset</button>
        <button :disabled="currentPage >= maximumPage - 1" @click="handleNext">Next</button>
        <button :disabled="currentPage === 0" @click="handlePrevious">Previous</button>
      </td>
    </tr>
  </table>
</template>

<script>
import categoryContainer from "@/components/main/category-container";
import personalSpacePanel from "@/components/main/personal-space-panel";
import itemTable from "@/components/main/item-table";
import {computed} from "vue";
import {getCookie} from "@/cookies";
import {router} from "@/router";

export default {
  components: {
    categoryContainer,
    personalSpacePanel,
    itemTable
  },
  data() {
    return {
      itemName: null,
      itemCategory: null,
      currentPage: 0,
      maximumPage: null,

      categories: null,
      items: null
    }
  },
  provide() {
    return {
      choice: computed(() => this.itemCategory),
      items: computed(() => this.items)
    }
  },
  methods: {
    handleChoice(category) {
      this.itemCategory = category;
    },
    handleSearch() {
      this.currentPage = 0;
      let url = new URL('http://localhost:8080/items/favourites');
      let params = [];
      if (this.itemCategory !== null) {
        params.push(['category', this.itemCategory])
      }
      if (this.itemName !== null) {
        params.push(['name', this.itemName])
      }
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
        },
      })
          .then((response) => response.json())
          .then((data) => {
            this.items = data.content;
            this.maximumPage = data.totalPages;
          });
    },
    handleReset() {
      this.itemName = null;
      this.itemCategory = null;
    },
    handleNext() {
      this.currentPage++;

      let url = new URL('http://localhost:8080/items/favourites');
      let params = [['pageNumber', this.currentPage]]
      if (this.itemCategory !== null) {
        params.push(['category', this.itemCategory])
      }
      if (this.itemName !== null) {
        params.push(['name', this.itemName])
      }
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
        },
      })
          .then((response) => response.json())
          .then((data) => this.items = data.content);
    },
    handlePrevious() {
      if (this.currentPage > 0) {
        this.currentPage--;
      }

      let url = new URL('http://localhost:8080/items/favourites');
      let params = [['pageNumber', this.currentPage]]
      if (this.itemCategory !== null) {
        params.push(['category', this.itemCategory])
      }
      if (this.itemName !== null) {
        params.push(['name', this.itemName])
      }
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
        },
      })
          .then((response) => response.json())
          .then((data) => this.items = data.content);
    },
    handleNavigate() {
      router.push('/main');
    }
  },
  mounted() {
    fetch('http://localhost:8080/items/favourites/categories', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
      },
    })
        .then((response) => response.json())
        .then((data) => this.categories = data);

    fetch('http://localhost:8080/items/favourites', {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
      },
    })
        .then((response) => response.json())
        .then((data) => {
          this.items = data.content;
          this.maximumPage = data.totalPages;
        });
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

p {
  text-align: center;
}

tr, td, th {
  border-radius: 12px;
  background-color: wheat;
}

div.list-wrapper {
  overflow: hidden;
  overflow-y: scroll;
  height: 150px;
}
</style>