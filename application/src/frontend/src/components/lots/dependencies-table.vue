<template>
    <div class="table-wrapper">
        <table>
            <tr>
                <th @click="sortByName">Name</th>
                <th @click="sortByNestingLevel">Nesting level</th>
            </tr>
            <dependencies-container v-for="dependency in dependenciesView" :key="dependency" :content="dependency" />
        </table>
    </div>
</template>

<script>
import dependenciesContainer from "@/components/lots/dependencies-container"

export default {
    inject: ['dependencies'],
    components: {
        dependenciesContainer
    },
    data() {
        return {
            currentSort: null,
        }
    },
    computed: {
        dependenciesView() {
            if (this.currentSort == 'name') {
                return structuredClone(this.dependencies).sort((a, b) => {
                    if (a.name > b.name) return 1;
                    if (a.name < b.name) return -1;
                    return 0;
                });
            }

            if (this.currentSort == 'level') {
                return structuredClone(this.dependencies).sort((a, b) => {
                    a = a.level;
                    b = b.level;

                    if (a > b) return 1;
                    if (a < b) return -1;
                    return 0;
                });
            }

            return this.dependencies;
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