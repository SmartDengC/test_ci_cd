(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["home"],{"22d5":function(t,e,n){"use strict";var i=n("37f1"),s=n.n(i);s.a},"33b2":function(t,e,n){},"37f1":function(t,e,n){},"3e59":function(t,e,n){"use strict";var i=n("55c4"),s=n.n(i);s.a},"55c4":function(t,e,n){},7159:function(t,e,n){t.exports=n.p+"static/img/img.146655c9.jpg"},b0c0:function(t,e,n){var i=n("83ab"),s=n("9bf2").f,l=Function.prototype,a=l.toString,o=/^\s*function ([^ (]*)/,c="name";!i||c in l||s(l,c,{configurable:!0,get:function(){try{return a.call(this).match(o)[1]}catch(t){return""}}})},bfe9:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"wrapper"},[n("v-head"),n("v-sidebar"),n("div",{staticClass:"content-box",class:{"content-collapse":t.collapse}},[n("div",{staticClass:"content"},[n("transition",{attrs:{name:"move",mode:"out-in"}},[n("keep-alive",{attrs:{include:t.tagsList}},[n("router-view")],1)],1),n("el-backtop",{attrs:{target:".content"}})],1)])],1)},s=[],l=(n("b0c0"),function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"header"},[n("div",{staticClass:"collapse-btn",on:{click:t.collapseChage}},[t.collapse?n("i",{staticClass:"el-icon-s-unfold"}):n("i",{staticClass:"el-icon-s-fold"})]),n("div",{staticClass:"logo"},[t._v("生源分析系统")]),n("div",{staticClass:"header-right"},[n("div",{staticClass:"header-user-con"},[n("div",{staticClass:"btn-fullscreen",on:{click:t.handleFullScreen}},[n("el-tooltip",{attrs:{effect:"dark",content:t.fullscreen?"取消全屏":"全屏",placement:"bottom"}},[n("i",{staticClass:"el-icon-rank"})])],1),t._m(0),n("el-dropdown",{staticClass:"user-name",attrs:{trigger:"click"},on:{command:t.handleCommand}},[n("span",{staticClass:"el-dropdown-link"},[t._v(" "+t._s(t.username)+" "),n("i",{staticClass:"el-icon-caret-bottom"})]),n("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[n("a",{attrs:{href:"https://github.com/lin-xin/vue-manage-system",target:"_blank"}},[n("el-dropdown-item",[t._v("项目仓库")])],1),n("el-dropdown-item",{attrs:{divided:"",command:"changePass"}},[t._v("修改密码")]),n("el-dropdown-item",{attrs:{divided:"",command:"Allusers"}},[t._v("所有用户")]),n("el-dropdown-item",{attrs:{divided:"",command:"loginout"}},[t._v("退出登录")])],1)],1)],1)]),n("el-dialog",{attrs:{title:"修改密码",visible:t.dialogVisible,width:"30%","before-close":t.handleClose},on:{"update:visible":function(e){t.dialogVisible=e}}},[n("span",[t._v("这是一段信息")]),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(e){t.dialogVisible=!1}}},[t._v("取 消")]),n("el-button",{attrs:{type:"primary"},on:{click:function(e){t.dialogVisible=!1}}},[t._v("确 定")])],1)])],1)}),a=[function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"user-avator"},[i("img",{attrs:{src:n("7159")}})])}],o=n("2b0e"),c=new o["default"],r=c,u={data:function(){return{collapse:!1,fullscreen:!1,name:"linxin",message:2,dialogVisible:!1}},computed:{username:function(){var t=localStorage.getItem("ms_username");return t||this.name}},methods:{handleCommand:function(t){"loginout"==t&&(localStorage.removeItem("ms_username"),this.$router.push("/login")),"changePass"==t&&this.$router.push("/resetPass"),"Allusers"==t&&this.$router.push("/allusers")},collapseChage:function(){this.collapse=!this.collapse,r.$emit("collapse",this.collapse)},handleFullScreen:function(){var t=document.documentElement;this.fullscreen?document.exitFullscreen?document.exitFullscreen():document.webkitCancelFullScreen?document.webkitCancelFullScreen():document.mozCancelFullScreen?document.mozCancelFullScreen():document.msExitFullscreen&&document.msExitFullscreen():t.requestFullscreen?t.requestFullscreen():t.webkitRequestFullScreen?t.webkitRequestFullScreen():t.mozRequestFullScreen?t.mozRequestFullScreen():t.msRequestFullscreen&&t.msRequestFullscreen(),this.fullscreen=!this.fullscreen},handleClose:function(t){this.$confirm("确认关闭？").then((function(e){t()})).catch((function(t){}))}},mounted:function(){document.body.clientWidth<1500&&this.collapseChage()}},d=u,m=(n("3e59"),n("2877")),f=Object(m["a"])(d,l,a,!1,null,"b80938f4",null),p=f.exports,h=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"sidebar"},[n("el-menu",{staticClass:"sidebar-el-menu",attrs:{"default-active":t.onRoutes,collapse:t.collapse,"background-color":"#7D020B","text-color":"#fff","active-text-color":"#fff","unique-opened":"",router:""}},[t._l(t.items,(function(e){return[e.subs?[n("el-submenu",{key:e.index,attrs:{index:e.index}},[n("template",{slot:"title"},[n("i",{class:e.icon}),n("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.title))])]),t._l(e.subs,(function(e){return[e.subs?n("el-submenu",{key:e.index,attrs:{index:e.index}},[n("template",{slot:"title"},[t._v(t._s(e.title))]),t._l(e.subs,(function(e,i){return n("el-menu-item",{key:i,attrs:{index:e.index}},[t._v(t._s(e.title))])}))],2):n("el-menu-item",{key:e.index,attrs:{index:e.index}},[t._v(t._s(e.title))])]}))],2)]:[n("el-menu-item",{key:e.index,attrs:{index:e.index}},[n("i",{class:e.icon}),n("span",{attrs:{slot:"title"},slot:"title"},[t._v(t._s(e.title))])])]]}))],2)],1)},v=[],b=(n("ac1f"),n("5319"),{data:function(){return{collapse:!1,items:[{icon:"el-icon-s-home",index:"/main",title:"主页"},{icon:"el-icon-s-tools",index:"/dataimport",title:"数据导入与导出",subs:[{index:"/dataimport/basic",title:"基础配置"},{index:"/dataimport/importsituation",title:"数据导入"},{index:"/dataimport/detectionandUpload",title:"数据导出"}]},{icon:"el-icon-s-data",index:"/diplay",title:"数据展示",subs:[{index:"/diplay/boy-girl",title:"男女比例"},{index:"/diplay/age",title:"年龄分布"}]},{icon:"el-icon-s-marketing",index:"/analyze",title:"数据分析",subs:[{index:"/analyze/分析一",title:"分析一"},{index:"/analyze/分析二",title:"分析二"}]},{icon:"el-icon-s-tools",index:"/dataConfiguration",title:"数据配置",subs:[{index:"/dataConfiguration/userconfiguration",title:"用户配置"},{index:"/dataConfiguration/otherconfiguration",title:"其他配置"}]},{icon:"el-icon-circle-close",index:"/logout",title:"退出"}]}},computed:{onRoutes:function(){return this.$route.path.replace("/","main")}},created:function(){var t=this;r.$on("collapse",(function(e){t.collapse=e,r.$emit("collapse-content",e)}))}}),g=b,x=(n("22d5"),Object(m["a"])(g,h,v,!1,null,"640da05a",null)),C=x.exports,_={data:function(){return{tagsList:[],collapse:!1}},components:{vHead:p,vSidebar:C},created:function(){var t=this;r.$on("collapse-content",(function(e){t.collapse=e})),r.$on("tags",(function(e){for(var n=[],i=0,s=e.length;i<s;i++)e[i].name&&n.push(e[i].name);t.tagsList=n}))}},w=_,k=(n("ca6c"),Object(m["a"])(w,i,s,!1,null,null,null));e["default"]=k.exports},c0fd:function(t,e,n){},ca6c:function(t,e,n){"use strict";var i=n("33b2"),s=n.n(i);s.a},e5d7:function(t,e,n){"use strict";var i=n("c0fd"),s=n.n(i);s.a},eb0f:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"main-page"},[n("div",{ref:"myChart",staticClass:"BoyGirl-chart"})])},s=[],l={data:function(){return{title:""}},methods:{drawLine:function(){var t=this.$echarts.init(this.$refs.myChart);t.setOption({title:{text:"男女生比例",left:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b} : {c} ({d}%)"},legend:{bottom:10,left:"center",data:["男生","女生"]},series:[{type:"pie",name:"男女生比例",radius:"60%",center:["50%","50%"],selectedMode:"single",data:[{value:1e3,name:"男生"},{value:330,name:"女生"}],itemStyle:{emphasis:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]})}},mounted:function(){var t=this;this.$nextTick((function(){t.drawLine()}))}},a=l,o=(n("e5d7"),n("2877")),c=Object(o["a"])(a,i,s,!1,null,null,null);e["default"]=c.exports}}]);