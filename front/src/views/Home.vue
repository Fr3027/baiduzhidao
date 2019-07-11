<template>
  <div class="about">
    <v-card>
      <v-card-title>
        Nutrition
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
            <a :href="props.item.url">{{ props.item.title }}</a>
          </td>
          <td class="text-xs-left">
            <v-btn style="backgroundColor:red" @click="disable(props.item.problemId)">删除</v-btn>
          </td>
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
          :length="totalDesserts"
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
      ]
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
    }
  },
  mounted() {
    this.getDataFromApi().then(data => {
      this.desserts = data.items;
      this.totalDesserts = data.total;
    });
  },
  methods: {
    getDataFromApi() {
      this.loading = true;
      return new Promise((resolve, reject) => {
        const { sortBy, descending, page, rowsPerPage } = this.pagination;

        axios.get("http://localhost:8080/question/all").then(response => {
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
          var total = Math.ceil(items.length / 5);
          console.log(total);
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
      var url = "http://localhost:8080/question/disable?problemId=" + problemId;
      axios.get(url).then(response => {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      });
    }
  }
};
</script>

