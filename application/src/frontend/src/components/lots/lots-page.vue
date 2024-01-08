<template>
  <table id="frame">
    <tr>
      <th @click="handleNavigate" colspan="3" id="header"><b>Lots page</b></th>
    </tr>
    <tr>
      <td>
        <span class="name4">Name</span>
        <div>
          <span class="itn">{{ itemName }}</span>
        </div>
        <div>
          <input id="isFavourite" type="checkbox" @click="handleFavourite" />
          <label>Add to favoutite</label>
        </div>
      </td>
      <td colspan="2">
        <personal-space-panel />
      </td>
    </tr>
    <tr>

      <td>
        <span class="dep">Dependencies</span>
        <div class="list-wrapper">
          <table>
            <text v-if="!this.isDependencies">There is no dependencies for this item</text>
            <div v-else>
              <dependencies-table />
              <tr>
                <div>
                  <text>
                    Optimal selfprice: {{ optimalSelfprice }}
                  </text>
                </div>
              </tr>
            </div>
          </table>
        </div>

        <div v-if="this.isDependencies">
          <button :disabled="currentPageDependencies >= maximumPageDependencies - 1"
            @click="handleNextDependencies" class="next3">Next</button>
          <button :disabled="currentPageDependencies === 0" @click="handlePreviousDependencies" class="prev3">Previous</button>
        </div>

      </td>

      <td>
        <table>
          <text v-if="!this.isLots">There is no active lots for this item</text>
          <div v-else>
            <lots-table />
          </div>
        </table>

        <div v-if="this.isLots">
          <button :disabled="currentPageLots >= maximumPageLots - 1"
            @click="handleNextLots" class="next4">Next</button>
          <button :disabled="currentPageLots === 0" @click="handlePreviousLots" class="prev4">Previous</button>
        </div>

      </td>
      <td>
        <chart-page :itemId="this.itemId"/>
      </td>
    </tr>

    
  </table>
</template>

<script>
import personalSpacePanel from "@/components/main/personal-space-panel";
import dependenciesTable from "@/components/lots/dependencies-table"
import lotsTable from "@/components/lots/lots-table"
import chartPage from "@/components/lots/chart-page"

import { computed } from "vue";
import { router } from "@/router"
import { getCookie } from "@/cookies";



export default {
  components: {
    personalSpacePanel,
    dependenciesTable,
    lotsTable,
    chartPage
  },
  data() {
    return {
      itemId: this.$route.params.id,
      itemName: null,

      isDependencies: true,
      isLots: true,

      optimalSelfprice: '-',

      dependencies: null,
      lots: null,

      currentPageDependencies: 0,
      maximumPageDependencies: null,

      currentPageLots: 0,
      maximumPageLots: null,
    }
  },
  beforeRouteUpdate(to, from, next) {
    this.fetchDetails(to);
    next();
  },
  provide() {
    return {
      dependencies: computed(() => this.dependencies),
      lots: computed(() => this.lots)
    }
  },
  methods: {
    handleFavourite() {
      var remember = document.getElementById("isFavourite");
      if (remember.checked) {
        let url = new URL(`http://localhost:8080/items/favourites`);
        fetch(url, {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
          },
          body: JSON.stringify({ "item_id": this.itemId })
        })
        return
      }

      let url = new URL(`http://localhost:8080/items/favourites/${this.itemId}`);
      fetch(url, {
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
        },
      })

    },
    handleNextDependencies() {
      this.currentPageDependencies++;

      let url = new URL(`http://localhost:8080/dependencies/${this.itemId}`);
      let params = [['pageNumber', this.currentPageDependencies]]
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => this.dependencies = data.content);
    },
    handlePreviousDependencies() {
      if (this.currentPageDependencies > 0) {
        this.currentPageDependencies--;
      }

      let url = new URL(`http://localhost:8080/dependencies/${this.itemId}`);
      let params = [['pageNumber', this.currentPageDependencies]]
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => this.dependencies = data.content);
    },

    handleNextLots() {
      this.currentPageLots++;

      let url = new URL(`http://localhost:8080/lots/${this.itemId}`);
      let params = [['pageNumber', this.currentPageLots]]
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => this.lots = data.content);
    },
    handlePreviousLots() {
      if (this.currentPageLots > 0) {
        this.currentPageLots--;
      }

      let url = new URL(`http://localhost:8080/lots/${this.itemId}`);
      let params = [['pageNumber', this.currentPageLots]]
      url.search = new URLSearchParams(params).toString();

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => this.lots = data.content);
    },


    handleNavigate() {
      router.push('/main')
    },
    fetchDetails(to) {
      fetch(`http://localhost:8080/dependencies/${to.params.id}`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => {
          this.dependencies = data.content;
          if (this.dependencies.length == 0) {
            this.isDependencies = false
          }
          this.maximumPageDependencies = data.totalPages;
        });

      fetch(`http://localhost:8080/lots/${to.params.id}`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => {
          this.lots = data.content;
          if (this.lots.length == 0) {
            this.isLots = false
          }
          this.maximumPageLots = data.totalPages;
        });

      fetch(`http://localhost:8080/items/${to.params.id}`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
      })
        .then((response) => response.json())
        .then((data) => {
          this.itemName = data.name
        });
    }
  },
  mounted() {
    fetch(`http://localhost:8080/dependencies/${this.itemId}`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      },
    })
      .then((response) => response.json())
      .then((data) => {
        this.dependencies = data.content;
        if (this.dependencies.length == 0) {
          this.isDependencies = false
        }
        this.maximumPageDependencies = data.totalPages;
      });

    fetch(`http://localhost:8080/lots/${this.itemId}`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json'
      },
    })
      .then((response) => response.json())
      .then((data) => {
        this.lots = data.content;
        if (this.lots.length == 0) {
          this.isLots = false
        }
        this.maximumPageLots = data.totalPages;
      });


    fetch(`http://localhost:8080/items/${this.itemId}`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
      },
    })
      .then((response) => response.json())
      .then((data) => {
        this.itemName = data.name
        var remember = document.getElementById("isFavourite");
        remember.checked = data.is_favourite
      });

    fetch(`http://localhost:8080/items/selfprice/${this.itemId}`, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': 'Bearer ' + (getCookie("token") != null ? getCookie("token") : "")
      },
    })
      .then((response) => response.json())
      .catch(() => 
        this.optimalSelfprice = "It's impossible to calculate selfprice"
      )
      .then((data) => {
        this.optimalSelfprice = data
      })
  }
}
</script>

<style scoped>
@media (width < 877px) {
  #frame {
    width: 400px;
  }
}

@media (877px <=width < 1062px) {
  #frame {
    width: 600px;
  }
}

@media (1062px <=width) {
  #frame {
    width: 800px;
  }
}

#frame {
  margin: auto;
  height: auto;
  text-align: justify;
}

#header {
  color: rgb(255, 251, 0);
  font-size: medium;
  font-family: serif;
  text-align: center;
}

td p {
  padding-left: 3%;
  padding-right: 3%;
}

p {
  text-align: center;
}

tr,
td,
th {
  border-radius: 12px;
  background-color: wheat;
  background-image: url('~@/assets/image.jpg');
}

div.list-wrapper {
  overflow: hidden;
  /* overflow-y: scroll; */
  /* height: 150px; */
}

#frame input {
  color: rgb(255, 255, 255);
}
.dep {
  color: rgb(0, 0, 0);
  padding-left: 5px;
}
#frame text {
  color: rgb(0, 0, 0);
  margin-top: 5px;
}
.next3 {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}
.prev3 {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
}
.next4 {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
  margin-top: 5px;
}
.prev4 {
  background-color: black;
  color: white;
  margin-left: 10px;
  border-radius: 4px;
  padding: 2px 10px;
  margin-top: 5px;
}
.name4 {
  color: rgb(0, 0, 0);
  padding-left: 5px;
}
.itn {
  color: rgb(0, 0, 0);
  padding-left: 5px;
}
#frame label {
  color: rgb(0, 153, 38);
  padding-left: 5px;
}
</style>