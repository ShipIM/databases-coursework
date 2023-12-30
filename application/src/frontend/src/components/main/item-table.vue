<template>
  <span>Items</span>
  <div class="table-wrapper">
    <table>
      <tr>
        <th @click="sortByName">Name</th>
        <th @click="sortByQuality">Quality</th>
        <th @click="sortByLevel">Level</th>
      </tr>
      <item-container v-for="item in itemsView"
                          :key="item"
                          :content="item" />
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

<style>
  div.table-wrapper {
    overflow: hidden;
    overflow-y: scroll;
    height: 300px;
  }
</style>