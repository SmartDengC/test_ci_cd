(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2a56f5f2"],{"0605":function(e,r,t){},b2e1:function(e,r,t){"use strict";t.r(r);var o=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"page-register"},[t("div",[t("div",[t("p",[e._v("用户注册")]),t("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,"status-icon":"",rules:e.rules,"label-width":"80px"}},[t("el-form-item",{attrs:{label:"用户名",prop:"username"}},[t("el-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.ruleForm.username,callback:function(r){e.$set(e.ruleForm,"username",r)},expression:"ruleForm.username"}})],1),t("el-form-item",{attrs:{label:"密码",prop:"pass"}},[t("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.ruleForm.pass,callback:function(r){e.$set(e.ruleForm,"pass",r)},expression:"ruleForm.pass"}})],1),t("el-form-item",{attrs:{label:"确认密码",prop:"checkPass"}},[t("el-input",{attrs:{type:"password",autocomplete:"off"},model:{value:e.ruleForm.checkPass,callback:function(r){e.$set(e.ruleForm,"checkPass",r)},expression:"ruleForm.checkPass"}})],1),t("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[t("el-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.ruleForm.email,callback:function(r){e.$set(e.ruleForm,"email",r)},expression:"ruleForm.email"}})],1),t("el-form-item",{attrs:{label:"邀请码",prop:"code"}},[t("el-input",{attrs:{type:"text",autocomplete:"off"},model:{value:e.ruleForm.code,callback:function(r){e.$set(e.ruleForm,"code",r)},expression:"ruleForm.code"}})],1)],1)],1),t("el-button",{attrs:{type:"primary"},on:{click:function(r){return e.submitForm("ruleForm")}}},[e._v("提交")]),t("el-button",{on:{click:function(r){return e.resetForm("ruleForm")}}},[e._v("重置")])],1)])},l=[],s={data:function(){var e=this,r=function(e,r,t){""==r?t(new Error("请输入用户名")):t()},t=function(r,t,o){""===t?o(new Error("请输入密码")):(""!==e.ruleForm.checkPass&&e.$refs.ruleForm.validateField("checkPass"),o())},o=function(r,t,o){""===t?o(new Error("请再次输入密码")):t!==e.ruleForm.pass?o(new Error("两次输入密码不一致!")):o()},l=function(e,r,t){if(""==r)t(new Error("邮箱不能为空"));else{var o=/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;o.test(r)?t():t(new Error("请输入正确的邮箱格式"))}},s=function(e,r,t){""==r?t(new Error("邀请码不能为空")):t()};return{ruleForm:{pass:"",checkPass:"",username:"",code:"",email:""},rules:{username:[{required:!0,validator:r,trigger:"blur"}],pass:[{required:!0,validator:t,trigger:"blur"}],checkPass:[{required:!0,validator:o,trigger:"blur"}],email:[{required:!0,validator:l,trigger:"blur"}],code:[{required:!0,validator:s,trigger:"blur"}]}}},methods:{submitForm:function(e){this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;alert("submit!")}))},resetForm:function(e){this.$refs[e].resetFields()}}},a=s,u=(t("c4db"),t("2877")),i=Object(u["a"])(a,o,l,!1,null,null,null);r["default"]=i.exports},c4db:function(e,r,t){"use strict";var o=t("0605"),l=t.n(o);l.a}}]);