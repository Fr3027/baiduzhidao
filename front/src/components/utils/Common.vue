<script type="text/javascript">
// 定义一些公共的属性和方法
const httpUrl = "http://49.234.177.63:9090/";
function commonFun() {
  console.log("公共方法");
}
// 暴露出这些属性和方法
export default {
  httpUrl,
  commonFun
};
</script>