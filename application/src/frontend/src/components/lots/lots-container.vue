<template>
    <tr>
      <td>{{seller}}</td>
      <td>{{finishTime}}</td>
      <td>
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