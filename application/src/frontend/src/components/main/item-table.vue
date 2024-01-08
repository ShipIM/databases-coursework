<template>
  <span class="items">Items</span>
  <div class="table-wrapper">
    <table>
      <tr>
        <th @click="sortByName" class="name">Name</th>
        <th @click="sortByQuality" class="quality">Quality</th>
        <th @click="sortByLevel" class="level">Level</th>
      </tr>
      <item-container v-for="item in itemsView" :key="item" :content="item" />
    </table>
  </div>
</template>

<script>
import itemContainer from "@/components/main/item-container";
export default {
  inject: ['items'],
  components: {
    itemContainer
  },
  data() {
    return {
      currentSort: null,
    }
  },
  computed: {
    itemsView() {
      if (this.currentSort === 'name') {
        return structuredClone(this.items).sort((a, b) => {
          if (a.name > b.name) return 1;
          if (a.name < b.name) return -1;
          return 0;
        });
      }

      if (this.currentSort === 'quality') {
        return structuredClone(this.items).sort((a, b) => {
          a = JSON.parse(a.properties).quality;
          b = JSON.parse(b.properties).quality;

          if (a > b) return 1;
          if (a < b) return -1;
          return 0;
        });
      }

      if (this.currentSort === 'level') {
        return structuredClone(this.items).sort((a, b) => {
          a = JSON.parse(a.properties).itemLevel;
          b = JSON.parse(b.properties).itemLevel;

          if (a > b) return 1;
          if (a < b) return -1;
          return 0;
        });
      }

      return this.items;
    }
  },
  methods: {
    sortByName() {
      if (this.currentSort === 'name') {
        this.currentSort = null;
        return;
      }

      this.currentSort = 'name';
    },
    sortByQuality() {
      if (this.currentSort === 'quality') {
        this.currentSort = null;
        return;
      }

      this.currentSort = 'quality';
    },
    sortByLevel() {
      if (this.currentSort === 'level') {
        this.currentSort = null;
        return;
      }

      this.currentSort = 'level';
    }
  }
}
</script>

<style scoped>
div.table-wrapper {
  overflow: hidden;
  overflow-y: scroll;
  height: 300px;
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
.name {
  color: white;
  padding-left: 5px;
}

.quality {
  color: white;
  padding-left: 5px;
  
}

.level {
  color: white;
  padding-right:30px;
  padding-left: 30px;


}

.items{
  color: white;
  padding-left: 10px;
 
}
.table-wrapper {
  background-color: rgb(34, 23, 14);
  color: white;
}
</style>
