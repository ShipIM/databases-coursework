<template>
    <tr>
      <td v-bind:class="getClass()" @click="handleClick" class="category">{{content}}</td>
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

<style scoped>
  .chosen-one {
    color: red;
    font-size: medium;
    padding-left: 3%;
    padding-right: 3%;
    font-family: 'Times New Roman', Times, serif;
    text-align: center;
  }
  .category {
    background-color: rgb(0, 0, 0);
    border-radius: 3px;
    width: 10%;
    border-style: solid;
    border-width: 1px;
    border-color: rgb(54, 54, 54);
    padding: 2px 5px;

  }
</style>