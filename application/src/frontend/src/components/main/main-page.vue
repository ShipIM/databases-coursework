<template>
  <table id="frame">
    <tr>
      <th id="header"><b>Main page</b></th>
    </tr>
    <tr>
      <td>
        <personal-space-panel />
      </td>
    </tr>
    <tr>
      <td>
        <span>Name</span>
        <div>
          <input type="text" v-model="itemName">
        </div>
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
                                :ref="category"
                                @emit-category="handleChoice" />
          </table>
        </div>
      </td>
    </tr>
    <tr>
      <item-table />
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
        .then((data) => this.items = data.content);
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