<template>
  <tr @click="handleClick">
    <td>{{itemName}}</td>
    <td>{{itemQuality}}</td>
    <td>{{itemLevel}}</td>
  </tr>
</template>

<script>
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
      //TODO добавить сюда переход на страницу с лотами
    }
  }
}
</script>