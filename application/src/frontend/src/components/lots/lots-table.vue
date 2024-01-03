<template>
    <div class="table-wrapper">
        <table>
            <tr>
                <th @click="sortByName">Seller</th>
                <th >Finish time</th>
                <th>Current bid</th>
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
            if (this.currentSort == 'name') {
                return structuredClone(this.lots).sort((a, b) => {
                    if (a.name > b.name) return 1;
                    if (a.name < b.name) return -1;
                    return 0;
                });
            }

            if (this.currentSort == 'level') {
                return structuredClone(this.lots).sort((a, b) => {
                    a = a.level;
                    b = b.level;

                    if (a > b) return 1;
                    if (a < b) return -1;
                    return 0;
                });
            }

            return this.lots;
        }
    },
    methods: {
        sortByName() {
            if (this.currentSort === 'name') {
                this.currentSort = null;
                return;
            }

            this.currentSort = 'name';
        },
        sortByNestingLevel() {
            if (this.currentSort === 'level') {
                this.currentSort = null;
                return;
            }

            this.currentSort = 'level';
        }
    }
}
</script>

<style>
div.table-wrapper {
    overflow: hidden;
    /* overflow-y: scroll; */
    height: 300px;
}
</style>