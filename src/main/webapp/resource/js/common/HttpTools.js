// 异步请求，请求体参数为json格式
function async(method,url,json,suc,fai){
     return requestWithJson(method,true,url,json,suc,fai);
};

// 同步请求，请求体参数为json格式
function sync(method,url,json,suc,fai){
    requestWithJson(method,false,url,json,suc,fai);
};


// 求体参数为json格式
function requestWithJson(method,isAsync,url,json,suc,fai){

    var result="";
	$.ajax({
        type: method,
        contentType: "application/json;charset=utf-8",
        url:url,
        data:json,
        async:isAsync,

        beforeSend: function (XMLHttpRequest) {

            var callBack = function(data){

                 var header = new Object();

                 for(var i in data){
                    var item = data[i];

                    var value = localStorage.getItem(item.key);
                     XMLHttpRequest.setRequestHeader(item.key, value==null?"":value);
                 }
            };

            readJsonConfig("resource/json/header.json",callBack);
        },

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

    return result;
}


// 简单的请求，请求体不带参数
function simpleRequest(method,isAsync,url,suc,fai){

var result="";
	$.ajax({
        type: method,
        url:url,
        async:isAsync,

        beforeSend: function (XMLHttpRequest) {

                    var callBack = function(data){

                         var header = new Object();

                         for(var i in data){
                            var item = data[i];

                            var value = localStorage.getItem(item.key);
                             XMLHttpRequest.setRequestHeader(item.key, value==null?"":value);
                         }
                    };

                    readJsonConfig("resource/json/header.json",callBack);
       },

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

    return result;
}