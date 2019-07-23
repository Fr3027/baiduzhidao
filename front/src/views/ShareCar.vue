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
            <v-btn color="primary" dark class="mb-2" v-on="on">发起拼车</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">come on !</span>
            </v-card-title>
            <v-card-text>
              <v-form v-model="valid">
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.src"
                        label="起始地"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.des"
                        label="目的地"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-dialog
                        ref="dialog"
                        v-model="editedItem.modal"
                        :return-value.sync="editedItem.sdate"
                        persistent
                        lazy
                        full-width
                        width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="editedItem.sdate"
                            label="乘车日期"
                            prepend-icon="event"
                            readonly
                            v-on="on"
                            required
                            :rules="commonRules"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="editedItem.sdate" scrollable>
                          <v-spacer></v-spacer>
                          <v-btn flat color="primary" @click="editedItem.modal = false">Cancel</v-btn>
                          <v-btn
                            flat
                            color="primary"
                            @click="$refs.dialog.save(editedItem.sdate)"
                          >OK</v-btn>
                        </v-date-picker>
                      </v-dialog>
                    </v-flex>
                    <v-flex xs11 sm5 md12>
                      <v-dialog
                        ref="dialog_time"
                        v-model="editedItem.modal2"
                        :return-value.sync="editedItem.stime"
                        persistent
                        lazy
                        full-width
                        width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="editedItem.stime"
                            label="乘车时间"
                            prepend-icon="access_time"
                            readonly
                            v-on="on"
                            required
                            :rules="commonRules"
                          ></v-text-field>
                        </template>
                        <v-time-picker
                          v-if="editedItem.modal2"
                          v-model="editedItem.stime"
                          full-width
                        >
                          <v-spacer></v-spacer>
                          <v-btn flat color="primary" @click="editedItem.modal2 = false">Cancel</v-btn>
                          <v-btn
                            flat
                            color="primary"
                            @click="$refs.dialog_time.save(editedItem.stime)"
                          >OK</v-btn>
                        </v-time-picker>
                      </v-dialog>
                    </v-flex>
                    <v-flex xs12 sm6 md12>
                      <v-text-field
                        v-model="editedItem.description"
                        label="详细描述"
                        required
                        :rules="commonRules"
                      ></v-text-field>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-form>
            </v-card-text>

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
      <v-data-table
        :items="desserts"
        :pagination.sync="pagination"
        :total-items="totalDesserts"
        :loading="loading"
        :headers="headers"
        class="elevation-1"
      >
        <template v-slot:items="props">
          <td class="text-xs-left title">{{ props.item.src }}</td>
          <td class="text-xs-left">{{ props.item.des }}</td>
          <td class="text-xs-left">{{ props.item.sdate }}</td>
          <td class="text-xs-left">{{ props.item.stime }}</td>
          <td class="text-xs-left">{{ props.item.description }}</td>
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
      selected: [],
      headers: [
        {
          text: "起始位置",
          align: "left",
          sortable: false,
          value: "src"
        },
        {
          text: "终点位置",
          align: "left",
          sortable: false,
          value: "des"
        },
        {
          text: "乘车日期",
          align: "left",
          sortable: false,
          value: "sdate"
        },
        {
          text: "乘车时间",
          align: "left",
          sortable: false,
          value: "stime"
        },
        {
          text: "详细描述",
          align: "left",
          sortable: false,
          value: "description"
        }
      ],
      dialog: false,
      editedItem: {
        src: "",
        des: "",
        description: "",
        sdate: new Date(+new Date() + 8 * 3600 * 1000)
          .toISOString()
          .substr(0, 10),
        modal: false,
        stime: new Date(+new Date() + 8 * 3600 * 1000)
          .toISOString()
          .substr(11, 5),
        modal2: false
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
        src: this.editedItem.src,
        des: this.editedItem.des,
        sdate: this.editedItem.sdate,
        stime: this.editedItem.stime + ":00",
        description: this.editedItem.description
      };

      var url =
        "http://49.234.177.63:9090/sharecar/add?src=" +
        item.src +
        "&description=" +
        item.description +
        "&sdate=" +
        item.sdate +
        "&stime=" +
        item.stime +
        "&des=" +
        item.des;
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

        axios.get("http://49.234.177.63:9090/sharecar/all").then(response => {
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