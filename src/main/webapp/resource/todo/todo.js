var currentPage=1;
var totals=0;
var totalPages=0;
var isFirst=false;
var isLast=false;


function query(currentPage){

     var result;

     var handleSuccess = function(response){
            if(response !== undefined) {
                    try {

                       if(response.code==200){

                            // 内容相关
                            var data=eval(response.data);

                            var tbody = document.getElementById('tbody');

                            var tableContent='';
                            $(data).each(function (index){
                                var val=data[index];
                                var tr='<tr class="active text-center"> ';
                                tr+='<td> '+val.id+' </td>';
                                tr+='<td> '+val.title+' </td>';
                                tr+='<td> '+val.content+' </td>';
                                tr+='<td class="active text-left" style="width: 200px;"> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="del(this)">删除</button> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="edit(this)">编辑</button> '
                                +' </td>';

                                tr+='</tr>';

                                tableContent+=tr;
                            });
                            tbody.innerHTML = tableContent;

                           showMessage(response.message);

                            var pageInfo=eval(response.pageInfo);
                            flushPageInfo(pageInfo);
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
   async('POST',BASE_URL+'/todo/page.do',json,handleSuccess,handleFailure);

};

function del(val){
  commonDel(val,'/todo/delete.do');
};

function edit(val){
     getEditPage(val,"/resource/todo/edit.html");
};

// 用于详细页面的设值
function setDataForDetail(data){
  $("#id").attr("value",data.id);
  $("#id").attr("readonly","true");
  $("#title").attr("value",data.title);
  $("#content").attr("value",data.content);
  $("#parent_id").attr("value",data.parent_id);
  $("#parent_title").attr("value",data.parent_title);
}


function getById(){
    getDetail('/todo/get.do');
}


function doUpdate(json){
      commonUpdate('/todo/update.do',json);

};


function getAllIdAndTitle(){

 var handleSuccess = function(response){
            if(response !== undefined) {
                try {

                   if(response.code==200){
                      var parent_id_list = document.getElementById('parent_id_list');
                      var data=eval(response.data);

                      var lists = '';
                      $(data).each(function (index){
                      var val=data[index];
                      var row='<li><a data="'+val.id+'" onclick="selected(this)">'+val.title+'</a></li>';
                      lists+= row;
                      });
                      parent_id_list.innerHTML = lists;
                   }else{
                       showMessage(response.message);
                   }

                } catch(e) {
                    alert("error!"+e);
                    return false;
                }
            }
         };

      	var handleFailure = function(response){

      	    if(response.code==200){
      	      handleSuccess(response);
      	    }else{
      	     showMessage('抱歉，获取父列表失败了');
      	    }

      	};

      // 会进到 handleFailure 方法，有个异常: Error: Permission denied to access property "jQuery341098912248770553111"
      // 调试时选择异“异常处暂停”即可看到
      async('POST',BASE_URL+'/todo/getAllIdAndTitle.do',handleSuccess,handleFailure);


}

// 下拉选项设值使用
function selected(val){
 var data=$(val).attr('data');
 $("#parent_id").attr("value",data);
  $("#parent_title").attr("value",val.text);
 //$("#parent_title").attr("value",$(val).attr('text'));
}
