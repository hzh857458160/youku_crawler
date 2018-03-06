/**
 * 发送输入的key值给后端
 */

function sendKeyByJson() {
    var key = document.getElementById("input-1").value;
    if(!checkInputKey(key)){
        alert("关键字不能为空哦");
    }else{
        $.ajax({
            type: 'post',
            url: '/search',
            dataType: 'json',
            data: {
                "search_key": key
            }
        }).done(function (data) {
                console.log('成功, 收到的数据: ' + JSON.stringify(data, null, '  '));
                var result = data;
                if (result.ok === "true") {
                    console.log("后端成功收到key");
                    window.location.href = '/search/wait';
                } else {
                    console.log("发送失败");
                }
        });
    }
}
/**
 *  检查输入框是否为空
 * @param val
 */
function checkInputKey(val) {
    var str = val.replace(/(^\s*)|(\s*$)/g, '');//去除空格;
    if (str === '' || str === undefined || str === null) {
        return false;
    }else{
        return true;
    }
}