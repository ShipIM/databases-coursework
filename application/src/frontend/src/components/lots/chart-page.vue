<template>
    <div>
        <div>
            <apexchart type="line" height="350" ref='priceChart' :options="chartOptions" :series="series" />
        </div>
        <div>
            <apexchart type="line" ref="quantityChart" :options="chartOptionsLine2" :series="seriesLine2" />
        </div>
    </div>
</template>
  
<script>
import VueApexCharts from "vue3-apexcharts";
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
    props: {
        itemId: String
    },
    components: {
        apexchart: VueApexCharts
    },
    data() {
        return {
            itemsForPeriod: null,
            days: [],
            maxCostBuys: [],
            quantities: [],

            series: [{
                name: "Price",
                data: [],
                },
            ],
            chartOptions: {
                chart: {
                    type: 'line',
                    zoom: {
                        enabled: false,
                    },
                    id: '1',
                    group: 'social'
                },
                
                title: {
                    text: 'Monthly product sales',
                    align: 'mid'
                },
               
                yaxis: {
                    title: {
                        text: 'Price'
                    },
                },
                xaxis: {
                    categories: [],
                    type: 'datetime',
                    title: {
                        text: 'Date'
                    }
                },
            },

            seriesLine2: [{
                name: "Quantity",
                data: [],
            }],
            chartOptionsLine2: {
                colors: ['#B844F2'],
                chart: {
                    type: 'line',
                    zoom: {
                        enabled: false,
                    },
                    id: '2',
                    group: 'social'
                },
                title: {
                    text: 'Monthly product sales quantity',
                    align: 'mid'
                },
                yaxis: {
                    title: {
                        text: 'Quantity'
                    },
                },
                xaxis: {
                    categories: [],
                    type: 'datetime',
                    title: {
                        text: 'Date'
                    }
                },
            },
        }
    },
    methods: {
        async setData() {
            let url = new URL('http://localhost:8080/items/items-for-period')
            await fetch(url, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ "item_id": this.itemId, "start": '2023-12-01', "end": '2023-12-31' })
            })
                .then((response) => response.json())
                .catch(() => console.log("error"))
                .then((date) => {
                    if (date == undefined) return
                    this.itemsForPeriod = date.sort((a, b) => {
                        if (a.day > b.day) return 1;
                        if (a.day < b.day) return -1;
                        return 0;
                    })

                    for (let key in this.itemsForPeriod) {
                        let day = this.itemsForPeriod[key].day
                        let arr = day.split("-")
                        let newDay = arr[1] + "/" + arr[2] + "/" + arr[0]
                        this.days.push(newDay)
                        this.maxCostBuys.push(this.itemsForPeriod[key].maxCostBuy)
                        this.quantities.push(this.itemsForPeriod[key].quantity)
                    }

                    this.$refs.priceChart.updateSeries([
                        {
                            data: this.maxCostBuys,
                        },
                    ])
                    this.$refs.quantityChart.updateSeries([
                        {
                            data: this.quantities
                        }
                    ])
                    this.$refs.priceChart.updateOptions({
                        xaxis: {
                            categories: this.days,
                        }
                    })
                    this.$refs.quantityChart.updateOptions({
                        xaxis: {
                            categories: this.days,
                        }
                    })
                });


            this.loaded = true
        }
    },
    async mounted() {
        this.setData()
    },
}
</script>