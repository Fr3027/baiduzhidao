<template>
  <div class="about">
    <v-card>
      <v-card-title>
        <v-btn fab class="skyblue" @click="changeStatus">收藏</v-btn>
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search" single-line hide-details></v-text-field>
      </v-card-title>
      <v-data-table
        :items="desserts"
        :pagination.sync="pagination"
        :total-items="totalDesserts"
        :loading="loading"
        :headers="headers"
        class="elevation-1"
      >
        <template v-slot:items="props">
          <td class="text-xs-left title">
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <span v-on="on">{{goodtitle(props.item.title)}}</span>
              </template>
              <span>{{props.item.title}}</span>
            </v-tooltip>
          </td>
          <td class="text-xs-right">
            <v-btn
              small
              style="backgroundColor:green"
              @click="baidu(props.item.title,props.item.url)"
            >GO</v-btn>
            <v-btn small style="backgroundColor:red" @click="disable(props.item.problemId)">删除</v-btn>
            <v-btn small style="backgroundColor:blue" @click="addHeart(props.item.problemId)">收藏</v-btn>
          </td>
          <!-- <td class="text-xs-left">
            
          </td>-->
        </template>
        <template v-slot:no-results>
          <v-alert
            :value="true"
            color="error"
            icon="warning"
          >Your search for "{{ search }}" found no results.</v-alert>
        </template>
      </v-data-table>

      <div class="text-xs-center pt-2">
        <v-pagination
          v-model="pagination.page"
          :length="Math.ceil(totalDesserts/5)"
          circle
          dark
          total-visible="7"
        ></v-pagination>
      </div>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";
import common from "../components/utils/Common.vue";
export default {
  data() {
    return {
      search: "",
      totalDesserts: 0,
      desserts: [],
      loading: true,
      pagination: {},
      selected: [],
      headers: [
        {
          text: "题目",
          align: "left",
          sortable: false,
          value: "title"
        },
        {
          text: "操作",
          align: "left",
          sortable: false,
          value: "operation"
        }
      ],
      ur: common.httpUrl,
      status: 0
    };
  },
  watch: {
    pagination: {
      handler() {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      },
      deep: true
    },
    search: {
      handler() {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      }
    },
    status: {
      handler() {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      }
    }
  },
  mounted() {
    this.getDataFromApi().then(data => {
      this.desserts = data.items;
      this.totalDesserts = data.total;
    });
  },

  methods: {
    goodtitle(title) {
      // console.log(title.length);
      if (title.length > 25) title = title.substr(0, 25) + "...";
      return title;
    },
    getDataFromApi() {
      this.loading = true;
      return new Promise((resolve, reject) => {
        const { sortBy, descending, page, rowsPerPage } = this.pagination;
        let url = this.ur + "question/all?status=" + this.status;
        axios.get(url).then(response => {
          this.loading = false;
          let search = this.search.trim().toLowerCase();
          var items = response.data;
          if (search) {
            items = items.filter(item => {
              return Object.values(item)
                .join(",")
                .toLowerCase()
                .includes(search);
            });
          }
          const total = items.length;
          if (rowsPerPage > 0) {
            items = items.slice((page - 1) * rowsPerPage, page * rowsPerPage);
          }

          resolve({
            items,
            total
          });
        });
      });
    },

    disable(problemId) {
      var url = this.ur + "question/disable?problemId=" + problemId;
      // "http://202.182.112.95:9090/question/disable?problemId=" + problemId;
      axios.get(url).then(response => {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      });
    },
    addHeart(problemId) {
      var url = this.ur + "question/heart?problemId=" + problemId;
      axios.get(url).then(response => {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      });
    },
    baidusearch(title) {
      var url =
        "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&ch=&tn=baiduhome_pg&bar=&wd=" +
        title +
        "&rsv_spt=1&oq=sdf&rsv_pq=c518c4120009815f&rsv_t=4ca763qsOQbrGNb4DDEri4ajwfN0DJ0nwBSum8RlNPW5wPga9Wo3BMQLeahaYQlCqIy3&rqlang=cn&rsv_enter=1&rsv_dl=tb&inputT=7627";
      window.open(url);
    },
    baidu(title, url) {
      var url1 =
        "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&ch=&tn=baiduhome_pg&bar=&wd=" +
        title +
        "&rsv_spt=1&oq=sdf&rsv_pq=c518c4120009815f&rsv_t=4ca763qsOQbrGNb4DDEri4ajwfN0DJ0nwBSum8RlNPW5wPga9Wo3BMQLeahaYQlCqIy3&rqlang=cn&rsv_enter=1&rsv_dl=tb&inputT=7627";
      window.open(url);
      window.open(url1);
    },
    changeStatus() {
      if (this.status == 0) this.status = 1;
      else this.status = 0;
    }
  }
};
</script>
<style scoped>
</style>


