// 顶部弹窗提示，1秒后自动消失，msg为提示内容
function showMessage(msg){
  $.globalMessenger().post({
     message: msg,
     hideAfter: 1,
     type: 'info'
  });
};

// 顶部弹窗提示，指定时间后自动消失，msg为提示内容，time为指定时间，单位：秒
function showMessageWithTimeOut(msg,time){
  $.globalMessenger().post({
     message: msg,
     hideAfter: time,
     type: 'info'
  });
};


function get(){
 query(currentPage);
};

// 访问首页
function getFirst(){

     if(isFirst){
       showMessage( '当前已经是首页了！');
        return ;
     }

    currentPage=1;
    get(currentPage,PAGE_SIZE);
};

// 访问末页
function getLast(){

     if(isLast){
        showMessage( '当前已经是末页了！');
        return ;
     }

    currentPage=totalPages;
    get(currentPage,PAGE_SIZE);
};

// 访问上一页
function getPrevious(){
    if(currentPage-1<1){
       showMessage( '不存在上一页！');
        return ;
    }
    currentPage=currentPage-1;
    get(currentPage,PAGE_SIZE);
};

// 访问下一页
function getNext(){
    if(currentPage+1>totalPages){
       showMessage( '不存在下一页！');
        return ;
    }
    currentPage=currentPage+1;
    get(currentPage,PAGE_SIZE);
};

// 刷新当前页面信息
function flushPageInfo(pageInfo){
   currentPage=pageInfo.currentPage;
   totals=pageInfo.totals;
   totalPages=pageInfo.totalPages;
   isFirst=pageInfo.isFirst;
   isLast=pageInfo.isLast;
   $('#info').text('第'+currentPage+'页/共'+totalPages+'页/总数'+totals);
};

// 获取编辑页面
function getEditPage(val,url){
    var id=$(val).attr('data');
    SELECT_ID = id;
    localStorage.setItem("SELECT_ID",id);
    window.location.href=BASE_URL+url;
};

// 获取普通页面
function getPage(url){
    window.location.href=BASE_URL+url;
};

// 获取详细页面
function getDetail(url){
    var handleSuccess = function(response){
         if(response !== undefined) {
             try {

                 if(response.code==200){
                    showMessage(response.message);

                    var data = response.data;

                   // 用于详情页的值设值
                   setDataForDetail(data);

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

    var SELECT_ID = localStorage.getItem("SELECT_ID");

    var json = '{"id":'+SELECT_ID+'}';
    async('POST',BASE_URL+url,json,handleSuccess,handleFailure);

}

// 通用的更新方法
function commonUpdate(url,json){
        var handleSuccess = function(response){
           if(response !== undefined) {
               try {

                  if(response.code==200){
                      showMessage(response.message);
                         //get();
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

     async('POST',BASE_URL+url,json,handleSuccess,handleFailure);

};

// 通用的添加方法
function commonAdd(url,json){
        var handleSuccess = function(response){
           if(response !== undefined) {
               try {

                  if(response.code==200){
                      showMessage(response.message);
                         //get();
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

     async('POST',BASE_URL+url,json,handleSuccess,handleFailure);

};

// 通用的删除方法
function commonDel(val,url){

    var id=$(val).attr('data');

    var handleSuccess = function(response){
                if(response !== undefined) {
                        try {

                           if(response.code==200){
                               showMessage(response.message);
                               get();
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

    var json = '{"id":'+id+'}';
     async('POST',BASE_URL+url,json,handleSuccess,handleFailure);
};

// 获取版权信息
function getCopyright(){
    return COPY_RIGHT;
}

// 设置分页
function setDefaultPagination(id){
   if(id==null){
    id='default_pagination';
   }

   var doc = document.getElementById(id);

   if(doc!=null){
      doc.innerHTML = GLOBAL_PAGINATION_HTML;
   }

}

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

// 设置导航
function setNavigation(){
  var el = document.getElementById('navigation');
  if(el!=null){
    el.innerHTML = NAVIGATION_HTML;
  }

}

// 构造导航代码
function generateNavigationHtml(configs ){

   var code = '<nav class="nav navbar-inverse navbar-fixed-top"> '
                    +'<div class="container"> '
                    +'<div id="menu" class="collapse navbar-collapse"> '
                    +'  <ul class="nav navbar-nav"> ';


   for(var i in configs){
        var item = configs[i];
        var id = item.id; // 元素id
        var name = item.name; // 导航名称
        var dropdown = item.dropdown; // 是否存在下拉项
        var url = item.url; // 跳转链接


        if(dropdown==true){

             var dropdown_items = item.dropdown_items;

             code += '              <li class="dropdown" id="'+id+'"> ';
             code += '                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ';
             code += '                      '+name+' ';
             code += '                      <span class="caret"></span> ';
             code += '                  </a> ';
             code += '                  <ul class="dropdown-menu"> ';

             for(var index in dropdown_items){

                 var id = dropdown_items[index].id; // 元素id
                 var name = dropdown_items[index].name; // 导航名称
                 var url = dropdown_items[index].url; // url

                 code += '                      <li><a href="'+ROOT_URL+url+'" id='+id+'>'+name+'</a></li> ';
             }

              code += '                  </ul> ';
               code += '              </li> ';

        }else{
             code += '              <li><a id="'+id+'" class="navbar-brand"  href="'+ROOT_URL+url+'">'+name+'</a></li> ';
        }
   }


    code += '      </ul> ';
    code += '  </div> ';
    code += '</div> ';
    code += '</nav>';

    return code;


}

// 读取菜单配置文件
function setNavigation(){

  var callBack = function(data){

       var code =  generateNavigationHtml(data);
       var el = document.getElementById('navigation');
       if(el!=null){
          el.innerHTML = code;
       }
  };

  readJsonConfig("../json/menu.json",callBack);

}

// 设置自定义的请求头信息，根据配置文件中设置的字段设值
function setCustomHeaderInfo(response){

  var callBack = function(data){

     for(var i in data){
        var item = data[i];
        var key = item.key;

        for(k in response){

             console.log('k='+k+',key='+key);


        }



         //localStorage.setItem("TOKEN",response.data.TOKEN);
         //localStorage.setItem("USER",response.data.USER);

     }
  };

  readJsonConfig(BASE_URL+"/resource/json/header.json",callBack);

}


function clearLocalStorage(){

  var callBack = function(data){

     for(var i in data){
        var item = data[i];
        var key = item.key;

        localStorage.removeItem(key);
     }
  };

  readJsonConfig(BASE_URL+"/resource/json/header.json",callBack);

}


// 读取本地json文件
function readJsonConfig(url,callBack){

        $.getJSON(url, function (data,status){
            if( status=='success'){
              var code =  callBack(data);
                return data;
            }else{
                 console.log(url+"文件读取失败："+status);
                 return false;
            }
        });


}


/**
initialPreview 示例:
                    ["https://picsum.photos/1920/1080?image=103"]
initialPreviewConfig 示例：
                   [{caption: "picture-3.jpg", size: 632762, width: "120px", url: '/teclan-spring-mvc/file/delete.do', key: 'keyString'}]
                    caption: 名称
                    url：删除的地址
                    key：删除的参数

*/
function getFileInputSetting(uploadUrl,extensions,uploadExtraData,initialPreview,initialPreviewConfig){

    var file_input = new Object();
    file_input.theme='explorer'; // 主题
    file_input.showUpload=false; // 是否显示“选择文件”按钮旁边的“上传”按钮
    file_input.showRemove=false; // 是否显示“选择文件”按钮旁边的“删除”按钮
    file_input.uploadUrl=uploadUrl; // 图片上传地址
    file_input.allowedFileExtensions=extensions;// 允许的文件后缀
    file_input.showCaption=true;//是否显示标题
    file_input.showPreview=true;// 是否预展示图片
    file_input.maxFileSize=1;//上传图片最大数量
    file_input.maxFileSize=0; //单位为kb，如果为0表示不限制文件大小
    file_input.enctype='multipart/form-data';// 上传图片的设置

    file_input.uploadExtraData=uploadExtraData;//上传路径的参数
    file_input.initialPreviewAsData= true;
    file_input.initialPreview=initialPreview;

    file_input.initialPreviewConfig= initialPreviewConfig;

    return file_input;

}

// 初始化页面
function initPage(){

  $.ajaxSettings.async = false;
  // 设置导航
  setNavigation();
  // 设置页脚
  setDefaultFooter();
  // 设置分页
  setDefaultPagination();

}


function openWindow(url,w,h)
{
    var left=(window.screen.width-w)/2;
    var top=(window.screen.height-h)/2;
    window.open(url, "", 'height='+h+', width='+w+',top='+top+',left='+left+',toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');

}


function getUrlParameter(name){
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);

   if (r != null) {
     return unescape(r[2]);
   }
   return null;
  }