<template>
    <tr>
      <td class="seller">{{seller}}</td>
      <td class="ft">{{finishTime}}</td>
      <td class="currentb">
        {{ current }} <br/>
        Buyout {{ buy }}
      </td>
    </tr>
</template>

<script>

export default {
  props: {
    content: JSON
  },
  data() {
    return {
      seller: this.content.vendor,
      finishTime: this.content.time_end,
        current: this.content.cost_current,
        buy: this.content.cost_buy
    }
  }
}
</script>
<style scoped>
.seller {
  background-color: rgb(0, 0, 0);
  color: rgb(255, 255, 255);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 20%;
}

.ft {
  background-color: rgb(0, 0, 0);
  color: rgb(255, 255, 255);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 20%;
}

.currentb {
  background-color: rgb(0, 0, 0);
  color: rgb(255, 255, 255);
  border-style: solid;
  border-width: 1px;
  border-color: rgb(54, 54, 54);
  padding: 2px 5px;
  width: 20%;
}

</style>