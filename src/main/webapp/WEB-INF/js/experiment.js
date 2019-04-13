// 创建一个变量
var ref = "";
// 定时刷新调用的方法
function keeplive(){
    $.ajax({
        type: "get",
        url: "jupyter/keeplive",
        cache: false,
        success: function (result) {
            console.log(result);
        }
    })
}
// 设置定时刷新(每两秒执行一次)
ref = setInterval(function(){
    keeplive();
}, 2000);