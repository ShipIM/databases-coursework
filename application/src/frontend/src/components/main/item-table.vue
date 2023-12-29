<template>
  <span>Items</span>
  <div class="table-wrapper">
    <table v-if="currentSort === null">
      <tr>
        <th @click="sortByName">Name</th>
        <th @click="sortByQuality">Quality</th>
        <th @click="sortByLevel">Level</th>
      </tr>
      <item-container v-for="item in items"
                          :key="item"
                          :content="item" />
    </table>
    <table v-if="currentSort !== 'null'">
      <tr>
        <th @click="sortByName">Name</th>
        <th @click="sortByQuality">Quality</th>
        <th @click="sortByLevel">Level</th>
      </tr>
      <item-container v-for="item in sortedItems"
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
      sortedItems: this.items
    }
  },
  methods: {
    sortByName() {
      if (this.currentSort === 'name') {
        this.currentSort = null;
        return;
      }

      this.sortedItems = structuredClone(this.items).sort((a, b) => {
        if (a.name > b.name) return 1;
        if (a.name < b.name) return -1;
        return 0;
      });

      this.currentSort = 'name';
    },
    sortByQuality() {
      if (this.currentSort === 'quality') {
        this.currentSort = null;
        return;
      }

      this.sortedItems = structuredClone(this.items).sort((a, b) => {
        a = JSON.parse(a.properties).quality;
        b = JSON.parse(b.properties).quality;

        if (a > b) return 1;
        if (a < b) return -1;
        return 0;
      });

      this.currentSort = 'quality';
    },
    sortByLevel() {
      if (this.currentSort === 'level') {
        this.currentSort = null;
        return;
      }

      this.sortedItems = structuredClone(this.items).sort((a, b) => {
        a = JSON.parse(a.properties).itemLevel;
        b = JSON.parse(b.properties).itemLevel;

        if (a > b) return 1;
        if (a < b) return -1;
        return 0;
      });

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