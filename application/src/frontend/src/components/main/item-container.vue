<template>
  <tr @click="handleClick">
    <td class="namee">{{itemName}}</td>
    <td class="qualityy">{{itemQuality}}</td>
    <td class="levell">{{itemLevel}}</td>
  </tr>
</template>

<script>
import { router } from '@/router';

export default {
  props: {
    content: JSON
  },
  data() {
    return {
      itemName: this.content.name,
      itemQuality: JSON.parse(this.content.properties).quality,
      itemLevel: JSON.parse(this.content.properties).itemLevel,
      itemId: this.content.id
    }
  },
  methods: {
    handleClick() {
      const itemId = this.itemId
      router.push({ name: 'lots', params: { id: itemId }})
    }
  }
}
</script>
<style scoped>
.namee {
  background-color: rgb(0, 0, 0);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 50%;
  border-radius: 3px 0 0 3px;
}
.qualityy {
  background-color: rgb(0, 0, 0);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 20%;
}
.levell {
  background-color: rgb(0, 0, 0);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 20%;
  border-radius: 0 3px 3px 0;
}
</style>