<template>
    <div class="table-wrapper">
        <table>
            <tr>
                <th @click="sortBySeller">Seller</th>
                <th @click="sortByTime">Finish time</th>
                <th @click="sortByBid">Current bid</th>
            </tr>
            <lots-container v-for="lot in lotsView" :key="lot" :content="lot" />
        </table>
    </div>
</template>

<script>
import lotsContainer from "@/components/lots/lots-container"

export default {
    inject: ['lots'],
    components: {
        lotsContainer
    },
    data() {
        return {
            currentSort: null,
        }
    },
    computed: {
        lotsView() {
            if (this.currentSort == 'seller') {
                return structuredClone(this.lots).sort((a, b) => {
                    if (a.vendor > b.vendor) return 1;
                    if (a.vendor < b.vendor) return -1;
                    return 0;
                });
            }

            if (this.currentSort == 'time') {
                return structuredClone(this.lots).sort((a, b) => {
                    a = a.time_end;
                    b = b.time_end;

                    if (a > b) return 1;
                    if (a < b) return -1;
                    return 0;
                });
            }

            if (this.currentSort == 'bid') {
                return structuredClone(this.lots).sort((a, b) => {
                    a = a.cost_current;
                    b = b.cost_current;

                    if (a > b) return 1;
                    if (a < b) return -1;
                    return 0;
                });
            }

            return this.lots;
        }
    },
    methods: {
        sortBySeller() {
            if (this.currentSort === 'seller') {
                this.currentSort = null;
                return;
            }

            this.currentSort = 'seller';
        },
        sortByTime() {
            if (this.currentSort === 'time') {
                this.currentSort = null;
                return;
            }

            this.currentSort = 'time';
        },
        sortByBid() {
            if (this.currentSort === 'bid') {
                this.currentSort = null;
                return;
            }

            this.currentSort = 'bid';
        }
    }
}
</script>

<style scoped>
div.table-wrapper {
    overflow: hidden;
    overflow-y: scroll;
    height: 300px;
}
</style>