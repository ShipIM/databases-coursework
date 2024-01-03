<template>
    <tr @click="handleClick">
      <td>{{itemName}}</td>
      <td>{{level}}</td>
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