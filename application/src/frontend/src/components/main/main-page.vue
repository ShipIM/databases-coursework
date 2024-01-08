<template>
  <table id="frame">
    <tr>
      <th colspan="2" id="header"><b>Main page</b></th>
    </tr>
    <tr>
      <td>
        <span class="names">Name</span>
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
        <span class="categories">Categories</span>
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
        <button @click="handleSearch" class="search">Search</button>
        <button @click="handleReset" class="reset">Reset</button>
        <button :disabled="currentPage === 0" @click="handlePrevious" class="previous">Prev</button>
        <button :disabled="currentPage >= maximumPage - 1" @click="handleNext" class="next">Next</button>
      </td>
    </tr>
  </table>
</template>

<script>
import categoryContainer from "@/components/main/category-container";
import personalSpacePanel from "@/components/main/personal-space-panel";
import itemTable from "@/components/main/item-table";
import {computed} from "vue";

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
      let url = new URL('http://localhost:8080/items');
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
          'Accept': 'application/json'
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

      let url = new URL('http://localhost:8080/items');
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
          'Accept': 'application/json'
        },
      })
          .then((response) => response.json())
          .then((data) => this.items = data.content);
    },
    handlePrevious() {
      if (this.currentPage > 0) {
        this.currentPage--;
      }

      let url = new URL('http://localhost:8080/items');
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
          'Accept': 'application/json'
        },
      })
          .then((response) => response.json())
          .then((data) => this.items = data.content);
    }
  },
  mounted() {
    fetch('http://localhost:8080/items/categories', {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      },
    })
        .then((response) => response.json())
        .then((data) => this.categories = data);

    fetch('http://localhost:8080/items', {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
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
}

#header {
  color: rgb(255, 251, 0);
  font-size: medium;
  font-family: serif;
  text-align: center;
  padding: 5px;
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
  background-image: url('~@/assets/image.jpg');
}
.categories {
  color: white;
  padding-left: 5px;
 
}
div.list-wrapper {
  overflow: hidden;
  overflow-y: scroll;
  height: 120px;
  background-color: rgb(34, 23, 14);
  color: rgb(255, 251, 0);
}
::-webkit-scrollbar {
  width: 15px;
  border-radius: 10px;
}

::-webkit-scrollbar-track {
  background-color: rgb(34, 23, 14)
}
::-webkit-scrollbar-thumb {
  background-color: rgb(0, 0, 0);
  border-radius: 0px;
  border: 1px solid rgb(54, 54, 54);
}

#frame input {
  margin-top: 0px;
  border: 10 rgb(171, 179, 179);
  background-color: rgb(10, 6, 0);
  color: aliceblue;
  outline: none;
  margin-left: 50px;
  border-radius: 4px;
}

#frame .names {
  color: rgb(255, 255, 255);
  text-align: left;
  padding-left: 50px;
}

#frame .reset {
  background-color: rgb(158, 0, 0);
  color: rgb(182, 168, 45);
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 15px;
}

#frame .search {
  background-color: rgb(158, 0, 0);
  color: rgb(182, 168, 45);
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 15px;
}

#frame .previous {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}

#frame .next {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}

</style>