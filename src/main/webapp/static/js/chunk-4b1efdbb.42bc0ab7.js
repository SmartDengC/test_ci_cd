(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4b1efdbb"],{"4c8c":function(t,e,s){"use strict";var a=s("983f"),r=s.n(a);r.a},"8aa2":function(t,e,s){"use strict";s.r(e);var a=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"page-test"},[s("div",[s("div",[s("div",{staticClass:"test-content"},[s("div",{staticClass:"test-top"}),s("div",{staticClass:"test-bottom"}),s("div",{staticClass:"login-wrap"},[s("div",{staticClass:"ms-login"},[s("p",{staticClass:"ms-title"},[t._v("生源分析系统")]),s("el-form",{ref:"login",staticClass:"ms-content",attrs:{model:t.param,rules:t.rules,"label-width":"0px"}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{attrs:{placeholder:"username"},model:{value:t.param.username,callback:function(e){t.$set(t.param,"username",e)},expression:"param.username"}},[s("template",{slot:"prepend"},[s("i",{staticClass:"el-icon-user"})])],2)],1),s("el-form-item",{attrs:{prop:"password"}},[s("el-input",{attrs:{type:"password",placeholder:"password"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.submitForm()}},model:{value:t.param.password,callback:function(e){t.$set(t.param,"password",e)},expression:"param.password"}},[s("template",{slot:"prepend"},[s("i",{staticClass:"el-icon-lock"})])],2)],1),s("div",{staticClass:"login-to"},[s("router-link",{attrs:{to:"/forgetPass"}},[t._v("忘记密码？")])],1)],1),s("div",{staticClass:"login-btn"},[s("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.submitForm()}}},[t._v("登录")]),s("router-link",{attrs:{to:"/register"}},[t._v("注册")])],1)],1)])])])])])},r=[],n={data:function(){return{param:{username:"admin",password:"123123"},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}]}}},methods:{submitForm:function(){var t=this;this.$refs.login.validate((function(e){if(!e)return t.$message.error("请输入账号和密码"),console.log("error submit!!"),!1;t.$message.success("登录成功"),localStorage.setItem("ms_username",t.param.username),t.$router.push("/")}))}}},i=n,o=(s("4c8c"),s("2877")),l=Object(o["a"])(i,a,r,!1,null,null,null);e["default"]=l.exports},"983f":function(t,e,s){}}]);