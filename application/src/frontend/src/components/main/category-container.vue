<template>
    <tr>
      <td v-bind:class="getClass()" @click="handleClick">{{content}}</td>
    </tr>
</template>

<script>
export default {
  inject: ['choice'],
  props: {
    content: String
  },
  methods: {
    handleClick() {
      this.$emit("emit-category", this.content)
    },
    getClass() {
      return {
        'chosen-one': this.choice === this.content
      }
    }
  }
}
</script>

<style>
  .chosen-one {
    color: red;
    font-size: medium;
    padding-left: 3%;
    padding-right: 3%;
    font-family: 'Times New Roman', Times, serif;
    text-align: center;
  }
</style>