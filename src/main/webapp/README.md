# 前端开发文档

## 后端跟路径配置

修改`resource\js\common\config.js`的`BASE_URL`项，形如:

``` 
// 后端服务跟路径
var BASE_URL="/teclan-spring-mvc";
```

## 分页大小


修改`resource\js\common\config.js`的`PAGE_SIZE`项，形如:

``` 
// 全局分页大小
var PAGE_SIZE=5";
```

## 版权

修改`resource\js\common\config.js`的`COPY_RIGHT`项，形如:

``` 
// 版权
var  COPY_RIGHT = "©2019 Teclan 广西xxxx公司";
```

## 页脚


页脚包含版权等信息，在 `resource\js\common\config.js`的`GLOBAL_FOOTER_HTML`项配置，
调用`initPage()` 会在`id`为`body`的元素后面追加

```
<footer id="default_footer" style="text-align:center">${你设置的版权信息}</footer>
```

对于设置页脚，我们提供了一下方法，以便灵活使用页脚，参考：

``` 
// 设置默认页脚（版权等）
function setDefaultFooter(id){

  if(id==null){
    id = 'body';
  }
  var doc = document.getElementById(id);
  if(doc!=null){
    doc.innerHTML += GLOBAL_FOOTER_HTML;
  }

}

```

实际上，`initPage()` 也是调用的了以上方法，只是指定的`id`为空，所有默人的效果就是
在`id`为`body`的元素中后面插入页脚。 


## 分页

我们对分页的插件做一些统一的封装，你仅仅需要在你的列表下方添加一个`id`为`default_pagination`的`div`，
就可以使用配置好的分页效果。

在`resource\js\common\config.js`的`GLOBAL_PAGINATION_HTML`项，配置了全局的分页代码，
统一格式，在任何需要的地方引入相关函数即可，分页的代码如下:

``` 
<nav aria-label="...">
  <ul class="pager">
      <li><a href="#" id="first" onclick="getFirst()">首页</a></li>
      <li><a href="#" id="previous" onclick="getPrevious()">上一页</a></li>
      <li><a href="#" id="info"></a></li>
      <li><a href="#" id="next" onclick="getNext()">下一页</a></li>
      <li><a href="#" id="last" onclick="getLast()">末页</a></li>
      </ul>'
  </nav>';
```

在每次更新列表完成后，请调用以下方法（该方法在`resource\js\common\base.js`中定义 ）
刷新分页信息（注意 `pageInfo`的格式,可自定义相关代码）:

``` 
// 刷新当前页面信息
function flushPageInfo(pageInfo){
   currentPage=pageInfo.currentPage;
   totals=pageInfo.totals;
   totalPages=pageInfo.totalPages;
   isFirst=pageInfo.isFirst;
   isLast=pageInfo.isLast;
   $('#info').text('第'+currentPage+'页/共'+totalPages+'页/总数'+totals);
};
```

在页脚代码中，涉及到的点击事件绑定的方法，均在`resource\js\common\base.js`中定义（请前往查阅详细代码），
这些方法，最终都进入到`query(currentPage)`方法，该方法每个业务需要单独实现，主要是用于填充列表，即关于
列表的操作，你仅需要实现`query(currentPage)`方法，相关的操作列（编辑和删除）和页脚代码你都不需要关心，
这些已经自动帮你完成，你仅仅需要在你的列表下方添加一个`id`为`default_pagination`的`div`，这个容器将用于
插入分页代码。完成这些以后，你只需要在页面初始化的时候调用`initPage()`方法即可（该方法在`resource\js\common\base.js`
中定义 ），如果你没有定义`id`为`default_pagination`的`div`容器，将不会帮你生成分页的插件。

## 导航

我们对导航插件做一些统一的封装，你仅仅需要在你需要的插入导航的地方添加一个`id`为`navigation`的`div`，
就可以使用配置好的导航效果。

菜单项，在`resource\json\menu.json` 文件中配置，支持并排的菜单和下拉菜单，其内容配置如下：

```json
[

  {"id": "home","name": "首页","dropdown": false,"url": "/resource/home/home.html"},
  {"id": "user","name": "用户","dropdown": false,"url": "/resource/user/userList.html"},
  {"id": "todo","name": "任务","dropdown": false,"url": "/resource/todo/todoList.html"},
  {"id": "dropdown1","name": "下拉测试","dropdown": true,"url":"",
                  "dropdown_items":[
                    {"id": "first","name": "firstName","dropdown": false,"url":""},
                    {"id": "second","name": "secondName","dropdown": false,"url":""}]
  }
]

```

我们将根据这个配置文件，自动生成导航栏，如果你想使用这个导航卡，仅需要在你的body里面添加以下代码即可:

```html
<div id="navigation"></div>

```

关于如何读取配置文件和生成导航栏的代码，请参考 `initPage()`->`setNavigation()`;

> 注意：在修改导航的跳转地址前，必须先调用`initPage()`初始化页面，因为该初始化页面
的方法是不含调整地址，如果先设置跳转地址再初始化页面，跳转地址会被置空，导致跳转失败。


## 请求头信息

支持通过配置文件动态设置请求头信息，该配置在`resource\json\header.json` 文件中配置，
其内容配置如下:


```json

[
  {"key": "USER","description": "当前登录用户"},
  {"key": "TOKEN","description": "登录成功获取到的token"}
]
```

关于头信息里面的值，我们是通过登录成功后将对应的键值从后端返回的信息中取出并缓存到`localStorage`，缓存到本地的代码
在登录成功后执行，请看代码：

``` 
function login(id,password){

     var result;

     var handleSuccess = function(data){
            if(data !== undefined) {
                    try {

                       if(data.code==200){

                       var callBack = function(configs){

                            // 遍历 header.json 里面配置的键
                            for(var i in configs){
                               var item = configs[i];
                               var key = item.key; /

                               var value = data.data[key]; // 获取登录返回的对应的键值
                               
                               // 缓存到 localStorage 
                               localStorage.setItem(key,value);

                            }
                        }

                         readJsonConfig(BASE_URL+"/resource/json/header.json",callBack);

                        showMessage(data.message);

                        //window.open(BASE_URL+"/resource/home/home.html");
                        window.location.href=BASE_URL+"/resource/home/home.html";

                        }else{
                           showMessage(data.message);
                        }


                    } catch(e) {
                        alert("error!"+e);
                        return false;
                    }
           }
      };

 	 var handleFailure = function(o){
 	 };

    var json = '{"id":"'+id+'","password":"'+password+'"}';

     $.ajaxSettings.async = false;

 	sync('POST',BASE_URL+'/user/login.do',json,handleSuccess,handleFailure);

};

```

而在发起`Ajax`请求时，向后台请求数据时，会自动带上头信息，请查看`resource\js\common\HttpTools.js`里面的方法，
最终的网络请求，将会调用该文件封装的网络请求方法：

``` 

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

            readJsonConfig("../json/header.json",callBack);
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

                    readJsonConfig("../json/header.json",callBack);
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

```

## 重要文件
 
 > resource\js\common\HttpTools.js
 > resource\js\common\base.js


### 示例

下面给出一个用户列表页面涉及到的代码

文件 `userList.html`:

```html

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">

    <!-- 这里引用公共的 css 文件（必须） -->
    <link rel="stylesheet" href="../css/common/include.css">
    
    <!-- 这里引用公共的 js 文件（必须） -->
    <script  language="javascript" type="text/javascript"  src="../js/common/include.js"></script>

    <!-- 这里引用你的 js 文件 -->
    <script  language="javascript" type="text/javascript"  src="user.js"></script>

    <title>用户管理</title>


</head>
<body id="body">

<script type="text/javascript">

    $(function () {
        <!-- 页面初始化，参考 base.js 中 initPage() 的定义-->
        initPage();
        <!-- 获取当前页，参考 base.js 中 get() 的定义-->
        get();
    });



</script>

<div id="navigation"></div>

<table class="table table-hover table-bordered" id="userList">
    <thead>
        <tr class="success">
            <th class="text-center">ID</th>
            <th class="text-center">姓名</th>
            <th class="text-center">电话</th>
            <th class="text-center">证件号</th>
            <th class="text-center">操作</th>
        </tr>
    </thead>

    <tbody id="tbody" class="active">

    </tbody>
    </tr>

</table>

<!-- 用于插入分页的容器 -->
<div id="default_pagination"></div>

</body>
</html>

```

文件 `user.js`:

```javascript

// ---------------------------------------------------------
// 这里是页脚相关的代码，每个页面复制一份即可，用于缓存页脚信息
var currentPage=1;
var totals=0;
var totalPages=0;
var isFirst=false;
var isLast=false;

// ---------------------------------------------------------

// 这个是列表页面必须实现的方法，每个业务单独实现，用于填充列表数据和刷新页脚
function query(currentPage){

     var result;

     var handleSuccess = function(response){
            if(response !== undefined) {
                    try {

                       if(response.code==200){

                            // ------------------------------------------------------------------------------------------------------
                            // 往列表中填充数据
                            var data=eval(response.data);

                            var tbody = document.getElementById('tbody');

                            var tableContent='';
                            $(data).each(function (index){
                                var val=data[index];
                                var tr='<tr class="active text-center"> ';
                                tr+='<td> '+val.id+' </td>';
                                tr+='<td> '+val.name+' </td>';
                                tr+='<td> '+val.phone+' </td>';
                                tr+='<td> '+val.id_card+' </td>';
                                tr+='<td class="active text-left" style="width: 200px;"> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="del(this)">删除</button> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="edit(this)">编辑</button> '
                                +' </td>';

                                tr+='</tr>';

                                tableContent+=tr;
                            });
                            tbody.innerHTML = tableContent;
                            // ------------------------------------------------------------------------------------------------------
                                                        

                            // -----------------------------------
                            // 弹窗提示信息，1s 后弹窗自动消失（可选）
                            showMessage(response.message);
                            // -----------------------------------
                            

                            // -----------------------------------
                            // 刷新页脚信息，如果确定只有一页，可忽略
                            var pageInfo=eval(response.pageInfo);
                            flushPageInfo(pageInfo);
                            // ---------------------------------
                       }else{
                            showMessage(response.message);
                       }

                    } catch(e) {
                        alert("error!"+e);
                        return false;
                    }
           }
      };

 	 var handleFailure = function(o){
 	 };

   var json = '{"currentPage":'+currentPage+',"pageSize":'+PAGE_SIZE+'}';
   async('POST',BASE_URL+'/user/page.do',json,handleSuccess,handleFailure);

};

// 删除，commonDel 方法在 base.js 中定义
function del(val){
     // 删除用户的url为 /user/delete.do 
     commonDel(val,'/user/delete.do');
};

// 编辑，getEditPage 方法在 base.js 中定义
function edit(val){
    // 编辑用户的页面为 /resource/user/edit.html
    getEditPage(val,"/resource/user/edit.html");
};

// 编辑，getPage 方法在 base.js 中定义
function add(){

    // 添加用户的页面为 /resource/user/edit.html
    getPage("/resource/user/add.html");
};

// 用于详细页面的设值
function setDataForDetail(data){
  $("#id").attr("value",data.id);
  $("#id").attr("readonly","true");
  $("#name").attr("value",data.name);
  $("#phone").attr("value",data.phone);
  $("#id_card").attr("value",data.id_card);
}

```