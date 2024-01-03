<template>
  <tr @click="handleClick">
    <td>{{itemName}}</td>
    <td>{{itemQuality}}</td>
    <td>{{itemLevel}}</td>
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