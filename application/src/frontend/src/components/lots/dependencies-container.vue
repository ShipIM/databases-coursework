<template>
    <tr @click="handleClick">
      <td class="name">{{itemName}}</td>
      <td class="nelevel">{{level}}</td>
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
      level: this.content.level,
      itemId: this.content.id,
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
.name {
  background-color: rgb(0, 0, 0);
  border-radius: 0px;
  width: 10%;
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
}
.nelevel {
  background-color: rgb(0, 0, 0);
  border-radius: 0px;
  width: 10%;
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
}
</style>