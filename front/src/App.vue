<template>
  <v-app id="inspire" class="grey lighten-2 grey--text">
    <v-navigation-drawer
      class="goodpurple"
      app
      v-model="drawer"
      :clipped="$vuetify.breakpoint.lgAndUp"
      fixed
    >
      <v-layout column align-center>
        <v-flex class="mt-2">
          <v-avatar size="100">
            <img src="./assets/cao.jpg" />
          </v-avatar>
          <p class="white--text subheading mt-1 text-md-center">我只是一根草</p>
        </v-flex>
      </v-layout>
      <v-list dense>
        <template v-for="item in items">
          <v-layout v-if="item.heading" :key="item.heading" row align-center>
            <v-flex xs6>
              <v-subheader v-if="item.heading">{{ item.heading }}</v-subheader>
            </v-flex>
            <v-flex xs6 class="text-xs-center">
              <a href="#!" class="body-2 black--text">EDIT</a>
            </v-flex>
          </v-layout>
          <v-list-group
            v-else-if="item.children"
            :key="item.text"
            v-model="item.model"
            :prepend-icon="item.model ? item.icon : item['icon-alt']"
            append-icon
            router
            :to="item.router"
          >
            <template v-slot:activator>
              <v-list-tile>
                <v-list-tile-content>
                  <v-list-tile-title>{{ item.text }}</v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </template>
            <v-list-tile v-for="(child, i) in item.children" :key="i">
              <v-list-tile-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>{{ child.text }}</v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
          </v-list-group>
          <v-list-tile v-else :key="item.text" router :to="item.router">
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>{{ item.text }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </template>
      </v-list>
    </v-navigation-drawer>
    <nav>
      <v-toolbar :clipped-left="$vuetify.breakpoint.lgAndUp" flat app>
        <v-toolbar-title style="width: 300px" class="ml-0 pl-3">
          <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
          <span class="hidden-sm-and-down">校萌</span>
        </v-toolbar-title>
        <v-text-field
          flat
          solo-inverted
          hide-details
          prepend-inner-icon="search"
          label="Search"
          class="hidden-sm-and-down"
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-btn flat>
          <span>登出</span>
          <v-icon right>exit_to_app</v-icon>
        </v-btn>
      </v-toolbar>
    </nav>

    <v-content class="mx-4 mb-4">
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => ({
    dialog: false,
    drawer: null,
    items: [
      { icon: "history", text: "个人中心", router: "/" },
      { icon: "history", text: "二手交易", router: "/second" },
      { icon: "history", text: "拼车", router: "/sharecar" },
      { icon: "history", text: "失物招领", router: "/b" },
      { icon: "history", text: "兼职发布", router: "/c" },
      { icon: "history", text: "常用网站", router: "/d" }
    ]
  }),
  props: {
    source: String
  }
};
</script>
<style>
.item {
  min-height: 50px;
  min-width: 80px;
  margin: 10px;
}
</style>
