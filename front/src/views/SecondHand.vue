<template>
  <div class="about" id="second">
    <v-card>
      <v-toolbar flat color="white">
        <v-spacer></v-spacer>
        <v-toolbar-title>
          <v-text-field
            v-model="search"
            append-icon="search"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark class="mb-2" v-on="on">发布物品</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">come on !</span>
            </v-card-title>
            <v-card-text>
              <!-- 发布二手商品的对话框 -->
              <v-form v-model="valid">
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.title"
                        label="标题"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.description"
                        label="描述"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-dialog
                        ref="dialog"
                        v-model="editedItem.modal"
                        :return-value.sync="editedItem.date"
                        persistent
                        lazy
                        full-width
                        width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="editedItem.date"
                            label=" 发布日期"
                            prepend-icon="event"
                            readonly
                            v-on="on"
                            required
                            :rules="commonRules"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="editedItem.date" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn flat color="primary" @click="editedItem.modal = false">Cancel</v-btn>
                          <v-btn flat color="primary" @click="$refs.dialog.save(editedItem.date)">OK</v-btn>
                        </v-date-picker>
                      </v-dialog>
                    </v-flex>
                    <v-flex xs11 sm5 md12>
                      <v-dialog
                        ref="dialog_time"
                        v-model="editedItem.modal2"
                        :return-value.sync="editedItem.time"
                        persistent
                        lazy
                        full-width
                        width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="editedItem.time"
                            label="具体时间"
                            prepend-icon="access_time"
                            readonly
                            v-on="on"
                            required
                            :rules="commonRules"
                          ></v-text-field>
                        </template>
                        <v-time-picker
                          v-if="editedItem.modal2"
                          v-model="editedItem.time"
                          full-width
                        >
                          <v-spacer></v-spacer>
                          <v-btn flat color="primary" @click="editedItem.modal2 = false">Cancel</v-btn>
                          <v-btn
                            flat
                            color="primary"
                            @click="$refs.dialog_time.save(editedItem.time)"
                          >OK</v-btn>
                        </v-time-picker>
                      </v-dialog>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.price"
                        label="价格"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-form>
            </v-card-text>
            <!-- 底部按钮 -->
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" flat @click="cancel">Cancel</v-btn>
              <v-btn color="blue darken-1" flat @click="save" :disabled="!valid">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-divider class="mx-2" inset vertical></v-divider>
        <v-spacer></v-spacer>
      </v-toolbar>
      <!-- 展示数据 -->
      <v-data-table
        :items="desserts"
        :pagination.sync="pagination"
        :total-items="totalDesserts"
        :loading="loading"
        :headers="headers"
        class="elevation-1"
      >
        <template v-slot:items="props">
          <td class="text-xs-left title">{{ props.item.title }}</td>
          <td class="text-xs-left">{{ props.item.description }}</td>
          <td class="text-xs-left">{{ props.item.sdate }}</td>
          <td class="text-xs-left">{{ props.item.stime }}</td>
          <td class="text-xs-left">{{ props.item.price }}元</td>
        </template>
        <template v-slot:no-results>
          <v-alert
            :value="true"
            color="error"
            icon="warning"
          >Your search for "{{ search }}" found no results.</v-alert>
        </template>
      </v-data-table>
      <!-- 底部分页条 -->
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
export default {
  name: "second",
  data() {
    return {
      valid: false,
      commonRules: [v => !!v || "此项不能为空！"],
      search: "",
      totalDesserts: 0,
      desserts: [],
      loading: true,
      pagination: {},
      headers: [
        {
          text: "物品",
          align: "left",
          sortable: false,
          value: "title"
        },
        {
          text: "描述",
          align: "left",
          sortable: false,
          value: "description"
        },
        {
          text: "发布日期",
          align: "left",
          sortable: false,
          value: "sdate"
        },
        {
          text: "具体时间",
          align: "left",
          sortable: false,
          value: "stime"
        },
        {
          text: "价格",
          align: "left",
          sortable: false,
          value: "description"
        }
      ],
      dialog: false,
      editedItem: {
        title: "",
        description: "",
        price: 0,
        date: new Date(+new Date() + 8 * 3600 * 1000)
          .toISOString()
          .substr(0, 10),
        modal: false,
        time: new Date(+new Date() + 8 * 3600 * 1000)
          .toISOString()
          .substr(11, 5),
        modal2: false,
        uid: 0
      }
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
    save() {
      let item = {
        title: this.editedItem.title,
        description: this.editedItem.description,
        sdate: this.editedItem.date,
        stime: this.editedItem.time + ":00",
        price: this.editedItem.price
      };

      var url =
        "http://49.234.177.63:9090/second/add?title=" +
        item.title +
        "&description=" +
        item.description +
        "&sdate=" +
        item.sdate +
        "&stime=" +
        item.stime +
        "&price=" +
        item.price +
        "&uid=0";
      axios.get(url).then(response => {
        this.getDataFromApi().then(data => {
          this.desserts = data.items;
          this.totalDesserts = data.total;
        });
      });
      this.dialog = false;
    },
    getDataFromApi() {
      this.loading = true;
      return new Promise((resolve, reject) => {
        const { sortBy, descending, page, rowsPerPage } = this.pagination;

        axios.get("http://49.234.177.63:9090/second/all").then(response => {
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
    cancel() {
      this.dialog = false;
    }
  },
  computed: {}
};
</script>