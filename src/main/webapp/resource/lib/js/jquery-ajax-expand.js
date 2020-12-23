/**
 * 重现ajax函数，检查返回值
 * Created by ly on 2016/11/2.
 */
jQuery(function($){
    var _ajax=$.ajax;
    // 重写ajax方法，先判断登录在执行success函数
    $.ajax=function(opt){
        var _success = opt && opt.success || function(a, b){};
        var _opt = $.extend(opt, {
            success:function(data, textStatus){
                // 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)
                if(typeof data === "object"){
                    if(data){
                        if(data.hasOwnProperty("response")){
                            var string = data["response"];
                            if(string.indexOf('session expired, please logon again') != -1) {
                                window.top.location.href = delUrlParam(window.top.location.href,"htmlrandom");
                                return;
                            }
                        }
                    }
                }
                _success(data, textStatus);
            }
        });
        _ajax(_opt);
    };
    function delUrlParam(url, ref){ //删除参数值
        var str = "";

        if (url.indexOf('?') != -1)
            str = url.substr(url.indexOf('?') + 1);
        else
            return url;
        var arr = "";
        var returnurl = "";
        var setparam = "";
        if (str.indexOf('&') != -1) {
            arr = str.split('&');
            for (i in arr) {
                if (arr[i].split('=')[0] != ref) {
                    returnurl = returnurl + arr[i].split('=')[0] + "=" + arr[i].split('=')[1] + "&";
                }
            }
            return url.substr(0, url.indexOf('?')) + "?" + returnurl.substr(0, returnurl.length - 1);
        }
        else {
            arr = str.split('=');
            if (arr[0] == ref)
                return url.substr(0, url.indexOf('?'));
            else
                return url;
        }
    }
});
/**********************************************************
 * 函数说明：发出异步请求，通过回调函数处理请求接收后的事
 * 创建人：ly
 * 创建时间：2016-11-2 15:22:07
 * 修改人：
 * 修改时间：
 * 修改内容：
 **********************************************************/
function post_async(params, url, callback,customData) {
    if(customData == undefined){
        customData = null;
    }
    $.ajax({
        type: 'POST',
        async: true,
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        url: url,
        success: function (data) {
            if (callback){
                callback(data,customData);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(window.console) console.error(XMLHttpRequest);
            if(window.console) console.error(textStatus);
            if(window.console) console.error(errorThrown);
            var data = {
                error:"ajax error"
            }
            if (callback){
                callback(data,customData);
            }
        }
    });
}
/**********************************************************
 * 函数说明：发出同步请求，将获取到的数据进行返回。
 * 创建人：ly
 * 创建时间：2016-11-2 15:23:10
 * 修改人：
 * 修改时间：
 * 修改内容：有返回值
 **********************************************************/
function post_sync(params, url) {
    var returnData = null;
    $.ajax({
        type: 'POST',
        async: false,
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        url: url,												//后台接口，url路径
        success: function (data) {
            returnData = data;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(window.console) console.error(XMLHttpRequest);
            if(window.console) console.error(textStatus);
            if(window.console) console.error(errorThrown);
            var data = {
                error:"ajax error"
            }
            if (data && callback)
                callback(data);
        }
    });
    return returnData;
}
function get_sync(url,dataType) {
    var returnData = null;
    $.ajax({
        type: 'GET',
        async: false,
        dataType: dataType || "text",
        url: url,												//后台接口，url路径
        success: function (data) {
            returnData = data;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(window.console) console.error(XMLHttpRequest);
            if(window.console) console.error(textStatus);
            if(window.console) console.error(errorThrown);
        }
    });
    return returnData;
}
/*测试效果使用*/
function get_async(url, callback,customData) {
    if(customData == undefined){
        customData = null;
    }
    $.ajax({
        type: 'GET',
        async: true,
        dataType: "json",
        url: url,
        success: function (data) {
            if (callback){
                callback(data,customData);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(window.console) console.error(XMLHttpRequest);
            if(window.console) console.error(textStatus);
            if(window.console) console.error(errorThrown);
            var data = {
                error:"ajax error"
            }
            if (callback){
                callback(data,customData);
            }
        }
    });
}

