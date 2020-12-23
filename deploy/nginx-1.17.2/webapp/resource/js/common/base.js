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